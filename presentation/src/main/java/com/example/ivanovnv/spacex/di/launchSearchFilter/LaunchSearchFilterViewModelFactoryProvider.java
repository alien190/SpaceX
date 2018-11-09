package com.example.ivanovnv.spacex.di.launchSearchFilter;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.di.launchDetail.LaunchDetailFragmentModule;
import com.example.ivanovnv.spacex.di.launchDetail.LaunchDetailViewModelFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

class LaunchSearchFilterViewModelFactoryProvider implements Provider<LaunchSearchFilterViewModelFactory> {
    private ILaunchService mLaunchService;
    private LaunchSearchFilter mLaunchSearchFilter;

    @Inject
    public LaunchSearchFilterViewModelFactoryProvider(ILaunchService launchService, LaunchSearchFilter launchSearchFilter) {
        mLaunchService = launchService;
        mLaunchSearchFilter = launchSearchFilter;
    }

    @Override
    public LaunchSearchFilterViewModelFactory get() {
        return new LaunchSearchFilterViewModelFactory(mLaunchService, mLaunchSearchFilter);
    }
}
