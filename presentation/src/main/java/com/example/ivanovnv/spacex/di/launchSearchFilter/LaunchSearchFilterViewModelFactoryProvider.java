package com.example.ivanovnv.spacex.di.launchSearchFilter;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterCallback;

import javax.inject.Inject;
import javax.inject.Provider;

class LaunchSearchFilterViewModelFactoryProvider implements Provider<LaunchSearchFilterViewModelFactory> {
    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;
    private ILaunchSearchFilterCallback mCallback;

    @Inject
    public LaunchSearchFilterViewModelFactoryProvider(ILaunchService launchService,
                                                      ICurrentPreferences currentPreferences,
                                                      ILaunchSearchFilterCallback callback) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mCallback = callback;
    }

    @Override
    public LaunchSearchFilterViewModelFactory get() {
        return new LaunchSearchFilterViewModelFactory(mLaunchService, mCurrentPreferences, mCallback);
    }
}
