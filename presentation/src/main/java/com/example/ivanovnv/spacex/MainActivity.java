package com.example.ivanovnv.spacex;


import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.di.launch.LaunchFragmentModule;

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
    }

    @Override
    protected void closeScope() {
        Toothpick.closeScope("LaunchFragment");
    }
}