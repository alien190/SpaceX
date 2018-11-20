package com.example.ivanovnv.spacex;


import android.support.v4.app.Fragment;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.di.launch.LaunchFragmentModule;
import com.example.ivanovnv.spacex.di.launchAnalytics.LaunchAnalyticsModule;

import toothpick.Scope;
import toothpick.Toothpick;

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void openScope() {
        Scope scope = Toothpick.openScopes("Application", "LaunchFragment");
        scope.installModules(new LaunchFragmentModule(this));

        scope = Toothpick.openScopes("Application", "AnalyticsFragment");
        scope.installModules(new LaunchAnalyticsModule(this, scope.getInstance(ILaunchService.class)));
    }

    @Override
    protected void closeScope() {
        Toothpick.closeScope("LaunchFragment");
        Toothpick.closeScope("AnalyticsFragment");
    }
}