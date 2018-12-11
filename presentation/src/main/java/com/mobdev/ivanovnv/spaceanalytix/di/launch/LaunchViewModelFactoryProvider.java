package com.mobdev.ivanovnv.spaceanalytix.di.launch;

import com.mobdev.domain.service.ILaunchService;

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
