package com.example.ivanovnv.spacex.di.launchAnalytics;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.di.launch.LaunchViewModelFactory;

import javax.inject.Inject;
import javax.inject.Provider;

class LaunchAnalyticsViewModelFactoryProvider implements Provider<LaunchAnalyticsViewModelFactory> {
    private ILaunchService mLaunchService;

    @Inject
    public LaunchAnalyticsViewModelFactoryProvider(ILaunchService launchService) {
        mLaunchService = launchService;
    }

    @Override
    public LaunchAnalyticsViewModelFactory get() {
        return new LaunchAnalyticsViewModelFactory(mLaunchService);
    }
}
