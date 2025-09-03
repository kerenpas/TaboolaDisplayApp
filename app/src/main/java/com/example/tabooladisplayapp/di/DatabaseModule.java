package com.example.tabooladisplayapp.di;

import android.content.Context;
import androidx.room.Room;
import com.example.tabooladisplayapp.data.local.AppDb;
import com.example.tabooladisplayapp.data.local.CellColorDao;
import com.example.tabooladisplayapp.data.local.FeedDao;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    AppDb provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDb.class, "feed.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    FeedDao provideFeedDao(AppDb database) {
        return database.feedDao();
    }

    @Provides
    @Singleton
    CellColorDao provideCellColorDao(AppDb database) {
        return database.cellColorDao();
    }
}
