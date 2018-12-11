package com.mobdev.ivanovnv.spaceanalytix.di.launchAnalytics;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;

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
