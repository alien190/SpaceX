package com.example.ivanovnv.spacex.di.launch;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;

import javax.inject.Inject;
import javax.inject.Provider;

class LaunchViewModelFactoryProvider implements Provider<LaunchViewModelFactory> {
    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;

    @Inject
    public LaunchViewModelFactoryProvider(ILaunchService launchService, ICurrentPreferences currentPreferences) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
    }

    @Override
    public LaunchViewModelFactory get() {
        return new LaunchViewModelFactory(mLaunchService, mCurrentPreferences);
    }
}
