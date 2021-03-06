package com.mobdev.ivanovnv.spaceanalytix;

import android.app.Application;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.di.application.ApplicationModule;
import com.mobdev.ivanovnv.spaceanalytix.di.application.DatabaseModule;
import com.mobdev.ivanovnv.spaceanalytix.di.application.NetworkModule;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;
import toothpick.Scope;
import toothpick.Toothpick;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

        //Stetho.initializeWithDefaults(this);
        Scope scope = Toothpick.openScope("Application");
        scope.installModules(new DatabaseModule(getApplicationContext()),
                new NetworkModule(),
                new ApplicationModule(getApplicationContext()));

        RxJavaPlugins.setErrorHandler(Timber::d);

        scope.getInstance(ILaunchService.class).refreshLaunches().subscribe();
    }


}
