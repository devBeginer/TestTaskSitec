package com.devbeginner.testtasksitec;

import android.app.Application;

import com.devbeginner.testtasksitec.di.Singleton;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Singleton.initDB(this);
    }
}
