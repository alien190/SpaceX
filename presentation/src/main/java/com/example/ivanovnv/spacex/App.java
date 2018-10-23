package com.example.ivanovnv.spacex;

import android.app.Application;

import com.example.ivanovnv.spacex.di.application.ApplicationModule;
import com.example.ivanovnv.spacex.di.application.DatabaseModule;
import com.example.ivanovnv.spacex.di.application.NetworkModule;
import com.facebook.stetho.Stetho;


import toothpick.Scope;
import toothpick.Toothpick;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Scope scope = Toothpick.openScope("Application");
        scope.installModules(new DatabaseModule(getApplicationContext()),
                new NetworkModule(),
                new ApplicationModule());
    }
}
