package com.example.tabooladisplayapp;

import android.app.Application;

import com.taboola.android.TBLPublisherInfo;
import com.taboola.android.Taboola;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class TaboolaApp extends Application {

    public TBLPublisherInfo tblPublisherInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        tblPublisherInfo = new TBLPublisherInfo("sdk-tester");
        Taboola.init(tblPublisherInfo);
    }
}
