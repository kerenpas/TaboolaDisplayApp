package com.example.tabooladisplayapp.presentation.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tabooladisplayapp.domain.usecase.UpdateCellColorUseCase;
import com.example.tabooladisplayapp.BuildConfig;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;



@AndroidEntryPoint
public class CellColorService extends Service {

    private static final String TAG = "CellColorService";

    @Inject
    UpdateCellColorUseCase updateCellColorUseCase;

    private final String securityToken = BuildConfig.SECURITY_TOKEN;

    private final ICellColorService.Stub binder = new ICellColorService.Stub() {
        @Override
        public boolean updateCellBackgroundColor(int position, String colorHex, boolean isVisible, String token) {
            if (!securityToken.equals(token)) {
                Log.w(TAG, "Invalid security token");
                return false;
            }

            try {
                final int color = Color.parseColor(colorHex);

                // Launch a new thread to perform the update operation
                new Thread(() -> {
                    try {
                        updateCellColorUseCase.invoke(isVisible, position, color);
                    } catch (Exception e) {
                        Log.e(TAG, "Error updating cell color", e);
                    }
                }).start();

                return true;
            } catch (IllegalArgumentException e) {
                Log.e(TAG, "Invalid color format", e);
                return false;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
