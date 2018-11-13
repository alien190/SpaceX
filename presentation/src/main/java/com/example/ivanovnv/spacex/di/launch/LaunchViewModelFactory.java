package com.example.ivanovnv.spacex.di.launch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.launch.LaunchViewModel;

public class LaunchViewModelFactory implements ViewModelProvider.Factory {

    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;

    public LaunchViewModelFactory(ILaunchService launchService, ICurrentPreferences currentPreferences) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LaunchViewModel(mLaunchService, mCurrentPreferences);
    }
}
