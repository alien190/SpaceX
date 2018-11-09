package com.example.ivanovnv.spacex.di.launchDetail;

import com.example.domain.service.ILaunchService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class LaunchDetailViewModelFactoryProvider implements Provider<LaunchDetailViewModelFactory> {
    private ILaunchService mLaunchService;
    private Integer mFlightNumber;

    @Inject
    public LaunchDetailViewModelFactoryProvider(ILaunchService launchService,
                                                @Named(LaunchDetailFragmentModule.FLIGHT_NUMBER_NAME)
                                                        Integer flightNumber) {
        mLaunchService = launchService;
        mFlightNumber = flightNumber;
    }

    @Override
    public LaunchDetailViewModelFactory get() {
        return new LaunchDetailViewModelFactory(mLaunchService, mFlightNumber);
    }
}
