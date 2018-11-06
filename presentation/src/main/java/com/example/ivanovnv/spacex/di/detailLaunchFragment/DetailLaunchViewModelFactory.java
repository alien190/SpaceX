package com.example.ivanovnv.spacex.di.detailLaunchFragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.detailLaunchFragment.DetailLaunchViewModel;

public class DetailLaunchViewModelFactory implements ViewModelProvider.Factory {

    private ILaunchService mLaunchService;
    private Integer mFlightNumber;

    public DetailLaunchViewModelFactory(ILaunchService launchService, Integer flightNumber) {
        mLaunchService = launchService;
        mFlightNumber = flightNumber;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new DetailLaunchViewModel(mLaunchService, mFlightNumber);

    }
}
