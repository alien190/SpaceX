package com.example.ivanovnv.spaceanalytix.di.launchDetail;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class LaunchDetailViewModelFactoryProvider implements Provider<LaunchDetailViewModelFactory> {
    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;
    private Integer mFlightNumber;

    @Inject
    public LaunchDetailViewModelFactoryProvider(ILaunchService launchService,
                                                ICurrentPreferences currentPreferences,
                                                @Named(LaunchDetailFragmentModule.FLIGHT_NUMBER_NAME)
                                                        Integer flightNumber) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mFlightNumber = flightNumber;
    }

    @Override
    public LaunchDetailViewModelFactory get() {
        return new LaunchDetailViewModelFactory(mLaunchService, mCurrentPreferences, mFlightNumber);
    }
}
