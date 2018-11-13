package com.example.ivanovnv.spacex.di.launchSearchFilter;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterCallback;
import com.example.ivanovnv.spacex.launchSearchFilter.LaunchSearchFilterViewModel;

public class LaunchSearchFilterViewModelFactory implements ViewModelProvider.Factory {

    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;
    private ILaunchSearchFilterCallback mCallback;

    public LaunchSearchFilterViewModelFactory(ILaunchService launchService,
                                              ICurrentPreferences currentPreferences,
                                              ILaunchSearchFilterCallback callback) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mCallback = callback;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LaunchSearchFilterViewModel(mLaunchService, mCurrentPreferences, mCallback);

    }
}
