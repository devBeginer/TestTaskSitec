package com.devbeginner.testtasksitec;

import android.app.Application;

import com.devbeginner.testtasksitec.di.DI;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DI.initDB(this);
    }
}
