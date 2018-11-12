package com.example.ivanovnv.spacex.di.launch;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.ivanovnv.spacex.launch.LaunchViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class LaunchViewModelProvider implements Provider<LaunchViewModel> {
    private FragmentActivity mActivity;
    private LaunchViewModelFactory mFactory;

    @Inject
    public LaunchViewModelProvider(FragmentActivity activity, LaunchViewModelFactory factory) {
        mActivity = activity;
        mFactory = factory;
    }

    @Override
    public LaunchViewModel get() {
        return ViewModelProviders.of(mActivity, mFactory).get(LaunchViewModel.class);
    }
}
