package com.example.ivanovnv.spacex.di.launchFragment;

import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.Launch.LaunchAdapter;
import com.example.ivanovnv.spacex.Launch.LaunchViewModel;
import com.example.ivanovnv.spacex.Launch.LaunchViewModelFactory;

import toothpick.config.Module;

public class LaunchFragmentModule extends Module {
    private Fragment mFragment;
    private LaunchAdapter mLaunchAdapter;

    public LaunchFragmentModule(Fragment fragment) {
        mFragment = fragment;
        mLaunchAdapter = new LaunchAdapter();
        bind(LaunchViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(LaunchViewModelFactory.class).toProvider(LaunchViewModelFactoryProvider.class);
        bind(LaunchAdapter.class).toInstance(mLaunchAdapter);
    }

}

