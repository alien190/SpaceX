package com.mobdev.ivanovnv.spaceanalytix.di.launchDetail;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;
import com.mobdev.ivanovnv.spaceanalytix.ui.launchDetail.LaunchDetailViewModel;

public class LaunchDetailViewModelFactory implements ViewModelProvider.Factory {

    private ILaunchService mLaunchService;
    private Integer mFlightNumber;
    private ICurrentPreferences mCurrentPreferences;

    public LaunchDetailViewModelFactory(ILaunchService launchService,
                                        ICurrentPreferences currentPreferences,
                                        Integer flightNumber) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mFlightNumber = flightNumber;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LaunchDetailViewModel(mLaunchService, mCurrentPreferences, mFlightNumber);

    }
}
