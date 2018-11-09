package com.example.ivanovnv.spacex.di.launchDetail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.launchDetail.LaunchDetailViewModel;

public class LaunchDetailViewModelFactory implements ViewModelProvider.Factory {

    private ILaunchService mLaunchService;
    private Integer mFlightNumber;

    public LaunchDetailViewModelFactory(ILaunchService launchService, Integer flightNumber) {
        mLaunchService = launchService;
        mFlightNumber = flightNumber;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new LaunchDetailViewModel(mLaunchService, mFlightNumber);

    }
}
