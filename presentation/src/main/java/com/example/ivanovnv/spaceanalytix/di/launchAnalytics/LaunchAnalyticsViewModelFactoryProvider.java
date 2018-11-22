package com.example.ivanovnv.spaceanalytix.di.launchAnalytics;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;

import javax.inject.Inject;
import javax.inject.Provider;

class LaunchAnalyticsViewModelFactoryProvider implements Provider<LaunchAnalyticsViewModelFactory> {
    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;

    @Inject
    public LaunchAnalyticsViewModelFactoryProvider(ILaunchService launchService, ICurrentPreferences currentPreferences) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
    }

    @Override
    public LaunchAnalyticsViewModelFactory get() {
        return new LaunchAnalyticsViewModelFactory(mLaunchService, mCurrentPreferences);
    }
}