package com.example.ivanovnv.spacex.di.launchAnalytics;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.example.ivanovnv.spacex.di.launch.LaunchViewModelFactory;
import com.example.ivanovnv.spacex.ui.launch.LaunchViewModel;
import com.example.ivanovnv.spacex.ui.launchAnalytics.LaunchAnalyticsViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class LaunchAnalyticsViewModelProvider implements Provider<LaunchAnalyticsViewModel> {
    private FragmentActivity mActivity;
    private LaunchAnalyticsViewModelFactory mFactory;

    @Inject
    public LaunchAnalyticsViewModelProvider(FragmentActivity activity, LaunchAnalyticsViewModelFactory factory) {
        mActivity = activity;
        mFactory = factory;
    }

    @Override
    public LaunchAnalyticsViewModel get() {
        return ViewModelProviders.of(mActivity, mFactory).get(LaunchAnalyticsViewModel.class);
    }
}
