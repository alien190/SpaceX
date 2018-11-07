package com.example.ivanovnv.spacex.di.launch;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.launch.LaunchViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class LaunchViewModelProvider implements Provider<LaunchViewModel> {
    private Fragment mFragment;
    private LaunchViewModelFactory mFactory;

    @Inject
    public LaunchViewModelProvider(Fragment fragment, LaunchViewModelFactory factory) {
        mFragment = fragment;
        mFactory = factory;
    }

    @Override
    public LaunchViewModel get() {
        return ViewModelProviders.of(mFragment, mFactory).get(LaunchViewModel.class);
    }
}
