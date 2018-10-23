package com.example.ivanovnv.spacex.di.launchFragment;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.Launch.LaunchViewModelFactory;

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
