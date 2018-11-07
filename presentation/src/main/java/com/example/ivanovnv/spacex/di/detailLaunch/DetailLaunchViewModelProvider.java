package com.example.ivanovnv.spacex.di.detailLaunch;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.detailLaunch.DetailLaunchViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class DetailLaunchViewModelProvider implements Provider<DetailLaunchViewModel> {
    private Fragment mFragment;
    private DetailLaunchViewModelFactory mFactory;

    @Inject
    public DetailLaunchViewModelProvider(Fragment fragment, DetailLaunchViewModelFactory factory) {
        mFragment = fragment;
        mFactory = factory;
    }

    @Override
    public DetailLaunchViewModel get() {
        return ViewModelProviders.of(mFragment, mFactory).get(DetailLaunchViewModel.class);
    }
}
