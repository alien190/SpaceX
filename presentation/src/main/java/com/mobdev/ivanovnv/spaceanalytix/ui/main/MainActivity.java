package com.mobdev.ivanovnv.spaceanalytix.ui.main;


import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.customComponents.SingleFragmentActivity;
import com.mobdev.ivanovnv.spaceanalytix.di.launch.LaunchFragmentModule;
import com.mobdev.ivanovnv.spaceanalytix.di.launchAnalytics.LaunchAnalyticsModule;

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
        //Toothpick.closeScope("LaunchFragment");
        //Toothpick.closeScope("AnalyticsFragment");
    }

    public static void start(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        } else {
            throw new IllegalArgumentException("context can't be null");
        }
    }
}