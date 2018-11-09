package com.example.ivanovnv.spacex.di.launchSearchFilter;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.launchSearchFilter.LaunchSearchFilterViewModel;

public class LaunchSearchFilterViewModelFactory implements ViewModelProvider.Factory {

    private ILaunchService mLaunchService;
    private LaunchSearchFilter mLaunchSearchFilter;

    public LaunchSearchFilterViewModelFactory(ILaunchService launchService, LaunchSearchFilter launchSearchFilter) {
        mLaunchService = launchService;
        mLaunchSearchFilter = launchSearchFilter;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LaunchSearchFilterViewModel(mLaunchService, mLaunchSearchFilter);

    }
}
