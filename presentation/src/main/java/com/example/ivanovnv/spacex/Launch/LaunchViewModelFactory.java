package com.example.ivanovnv.spacex.Launch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.service.LaunchService;

public class LaunchViewModelFactory implements ViewModelProvider.Factory {

    private LaunchService mLaunchService;

    public LaunchViewModelFactory(LaunchService launchService) {
        mLaunchService = launchService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LaunchViewModel(mLaunchService);
    }
}
