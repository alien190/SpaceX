package com.example.ivanovnv.spaceanalytix.di.launch;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spaceanalytix.ui.launch.LaunchViewModel;

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
