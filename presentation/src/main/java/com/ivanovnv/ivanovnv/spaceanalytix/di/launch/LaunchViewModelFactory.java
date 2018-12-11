package com.ivanovnv.ivanovnv.spaceanalytix.di.launch;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.ui.launch.LaunchViewModel;

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
