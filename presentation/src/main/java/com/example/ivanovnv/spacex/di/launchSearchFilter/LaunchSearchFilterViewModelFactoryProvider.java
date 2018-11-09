package com.example.ivanovnv.spacex.di.launchSearchFilter;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.di.launchDetail.LaunchDetailFragmentModule;
import com.example.ivanovnv.spacex.di.launchDetail.LaunchDetailViewModelFactory;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterCallback;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class LaunchSearchFilterViewModelFactoryProvider implements Provider<LaunchSearchFilterViewModelFactory> {
    private ILaunchService mLaunchService;
    private ILaunchSearchFilterCallback mCallback;

    @Inject
    public LaunchSearchFilterViewModelFactoryProvider(ILaunchService launchService,
                                                      ILaunchSearchFilterCallback callback) {
        mLaunchService = launchService;
        mCallback = callback;
    }

    @Override
    public LaunchSearchFilterViewModelFactory get() {
        return new LaunchSearchFilterViewModelFactory(mLaunchService, mCallback);
    }
}
