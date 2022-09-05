package com.devpk.feedme;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

public class App extends Application {

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
