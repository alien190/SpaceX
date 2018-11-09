package com.example.ivanovnv.spacex.di.launchSearchFilter;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.launchSearchFilter.LaunchSearchFilterViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class LaunchSearchFilterViewModelProvider implements Provider<LaunchSearchFilterViewModel> {
    private Fragment mFragment;
    private LaunchSearchFilterViewModelFactory mFactory;

    @Inject
    public LaunchSearchFilterViewModelProvider(Fragment fragment, LaunchSearchFilterViewModelFactory factory) {
        mFragment = fragment;
        mFactory = factory;
    }

    @Override
    public LaunchSearchFilterViewModel get() {
        return ViewModelProviders.of(mFragment, mFactory).get(LaunchSearchFilterViewModel.class);
    }
}
