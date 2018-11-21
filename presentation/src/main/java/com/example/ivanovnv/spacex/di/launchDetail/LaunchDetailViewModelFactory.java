package com.example.ivanovnv.spacex.di.launchDetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.ui.launchDetail.LaunchDetailViewModel;

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
