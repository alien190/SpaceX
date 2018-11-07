package com.example.ivanovnv.spacex.di.detailLaunch;

import com.example.domain.service.ILaunchService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class DetailLaunchViewModelFactoryProvider implements Provider<DetailLaunchViewModelFactory> {
    private ILaunchService mLaunchService;
    private Integer mFlightNumber;

    @Inject
    public DetailLaunchViewModelFactoryProvider(ILaunchService launchService,
                                                @Named(DetailLaunchFragmentModule.FLIGHT_NUMBER_NAME)
                                                        Integer flightNumber) {
        mLaunchService = launchService;
        mFlightNumber = flightNumber;
    }

    @Override
    public DetailLaunchViewModelFactory get() {
        return new DetailLaunchViewModelFactory(mLaunchService, mFlightNumber);
    }
}
