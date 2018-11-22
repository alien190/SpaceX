package com.example.ivanovnv.spaceanalytix.di.launchAnalytics;

import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.FragmentActivity;

import com.example.ivanovnv.spaceanalytix.ui.launchAnalytics.LaunchAnalyticsViewModel;

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
