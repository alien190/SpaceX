package com.example.ivanovnv.spacex.di.launchDetail;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.ui.launchDetail.LaunchDetailViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class LaunchDetailViewModelProvider implements Provider<LaunchDetailViewModel> {
    private Fragment mFragment;
    private LaunchDetailViewModelFactory mFactory;

    @Inject
    public LaunchDetailViewModelProvider(Fragment fragment, LaunchDetailViewModelFactory factory) {
        mFragment = fragment;
        mFactory = factory;
    }

    @Override
    public LaunchDetailViewModel get() {
        return ViewModelProviders.of(mFragment, mFactory).get(LaunchDetailViewModel.class);
    }
}
