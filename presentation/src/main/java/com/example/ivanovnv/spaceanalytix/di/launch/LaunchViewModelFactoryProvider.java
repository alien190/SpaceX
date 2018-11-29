package com.example.ivanovnv.spaceanalytix.di.launch;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;

import javax.inject.Inject;
import javax.inject.Provider;

class LaunchViewModelFactoryProvider implements Provider<LaunchViewModelFactory> {
    private ILaunchService mLaunchService;

    @Inject
    public LaunchViewModelFactoryProvider(ILaunchService launchService) {
        mLaunchService = launchService;
    }

    @Override
    public LaunchViewModelFactory get() {
        return new LaunchViewModelFactory(mLaunchService);
    }
}
