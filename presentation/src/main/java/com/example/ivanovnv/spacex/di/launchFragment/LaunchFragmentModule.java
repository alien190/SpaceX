package com.example.ivanovnv.spacex.di.launchFragment;

import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.Launch.LaunchAdapter;
import com.example.ivanovnv.spacex.Launch.LaunchViewModel;
import com.example.ivanovnv.spacex.common.LaunchLayoutManager;

import toothpick.config.Module;

public class LaunchFragmentModule extends Module {
    private Fragment mFragment;

    public LaunchFragmentModule(Fragment fragment) {
        mFragment = fragment;
        bind(LaunchViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(LaunchAdapter.class).toInstance(new LaunchAdapter());
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(LaunchViewModelFactory.class).toProvider(LaunchViewModelFactoryProvider.class);
    }

}

