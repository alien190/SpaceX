package com.example.ivanovnv.spacex.di.launch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.launch.LaunchViewModel;

public class LaunchViewModelFactory implements ViewModelProvider.Factory {

    private ILaunchService mLaunchService;

    public LaunchViewModelFactory(ILaunchService launchService) {
        mLaunchService = launchService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LaunchViewModel(mLaunchService);
    }
}