package com.example.tabooladisplayapp.data.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;
import com.example.tabooladisplayapp.BuildConfig;
import com.example.tabooladisplayapp.data.local.AppDb;
import com.example.tabooladisplayapp.data.local.CellColorEntity;
import java.util.List;
import androidx.sqlite.db.SupportSQLiteDatabase;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import com.example.tabooladisplayapp.domain.usecase.UpdateCellColorUseCase;


public class CellColorContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.tabooladisplayapp.cellcolorprovider";
    public static final String TABLE_NAME = "cellColor";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
    private static final int CELL_COLORS = 1;
    private static final int CELL_COLOR_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, CELL_COLORS);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", CELL_COLOR_ID);
    }
    private AppDb db;
    private UpdateCellColorUseCase updateCellColorUseCase;

    @EntryPoint
    @InstallIn(SingletonComponent.class)
    public interface UpdateCellColorUseCaseEntryPoint {
        UpdateCellColorUseCase updateCellColorUseCase();
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        if (context != null) {
            db = Room.databaseBuilder(context.getApplicationContext(), AppDb.class, "app_db").build();
            // Get UpdateCellColorUseCase from Hilt EntryPoint
            UpdateCellColorUseCaseEntryPoint entryPoint = EntryPointAccessors.fromApplication(
                context.getApplicationContext(), UpdateCellColorUseCaseEntryPoint.class);
            updateCellColorUseCase = entryPoint.updateCellColorUseCase();
            // Pre-populate cellColor table if empty
            new Thread(() -> {
                if (db.cellColorDao().getAll().isEmpty()) {
                    SupportSQLiteDatabase sqlDb = db.getOpenHelper().getWritableDatabase();
                    sqlDb.beginTransaction();
                    try {
                        for (int i = 0; i < 10; i++) {
                            sqlDb.execSQL("INSERT INTO cellColor (position, color, isVisible) VALUES (?, ?, ?)", new Object[]{i, 0, 1});
                        }
                        sqlDb.setTransactionSuccessful();
                    } finally {
                        sqlDb.endTransaction();
                    }
                }
            }).start();
        }
        return db != null && updateCellColorUseCase != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                       @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (uriMatcher.match(uri) == CELL_COLORS) {
            List<CellColorEntity> entities = db.cellColorDao().getAll();
            MatrixCursor cursor = new MatrixCursor(new String[]{"position", "color", "isVisible"});
            for (CellColorEntity entity : entities) {
                cursor.addRow(new Object[]{entity.position, entity.color, entity.isVisible ? 1 : 0});
            }
            return cursor;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    private boolean isTokenValid(ContentValues values) {
        if (values == null) return false;
        String token = values.getAsString("security_token");
        return BuildConfig.SECURITY_TOKEN.equals(token);
    }

    private boolean isTokenValid(Uri uri) {
        String token = uri.getQueryParameter("security_token");
        return BuildConfig.SECURITY_TOKEN.equals(token);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (!isTokenValid(values)) return null;
        if (uriMatcher.match(uri) == CELL_COLORS && values != null) {
            int position = values.getAsInteger("position");
            int color = values.getAsInteger("color");
            boolean isVisible = values.getAsBoolean("isVisible");
            CellColorEntity entity = new CellColorEntity(position, color, isVisible);
            db.cellColorDao().insert(entity);
            // Call use case after DB update
            updateCellColorUseCase.invoke(isVisible, position, color);
            return Uri.withAppendedPath(CONTENT_URI, String.valueOf(position));
        }
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        if (!isTokenValid(values)) return 0;
        if (uriMatcher.match(uri) == CELL_COLOR_ID && values != null) {
            int position = Integer.parseInt(uri.getLastPathSegment());
            int color = values.getAsInteger("color");
            boolean isVisible = values.getAsBoolean("isVisible");
            db.cellColorDao().updateColorAndVisibility(position, color, isVisible);
            // Call use case after DB update
            updateCellColorUseCase.invoke(isVisible, position, color);
            List<CellColorEntity> list = db.cellColorDao().getAll();
            list.size(); // ensure position is valid
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (!isTokenValid(uri)) return 0;
        // Not implemented
        return 0;
    }
}
