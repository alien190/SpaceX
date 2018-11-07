package com.example.ivanovnv.spacex.di.detailLaunch;

import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.detailLaunch.DetailLaunchViewModel;
import com.example.ivanovnv.spacex.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.common.LaunchLayoutManager;

import toothpick.config.Module;

public class DetailLaunchFragmentModule extends Module {
    public static final String FLIGHT_NUMBER_NAME = "FLIGHT_NUMBER";
    private Fragment mFragment;
    private Integer mFlightNumber;

    public DetailLaunchFragmentModule(Fragment fragment, Integer flightNumber) {
        mFragment = fragment;
        mFlightNumber = flightNumber;
        bind(DetailLaunchViewModel.class).toProvider(DetailLaunchViewModelProvider.class).providesSingletonInScope();
        bind(DetailLaunchViewModelFactory.class).toProvider(DetailLaunchViewModelFactoryProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(LaunchAdapter.class).toInstance(new LaunchAdapter());
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(Integer.class).withName(FLIGHT_NUMBER_NAME).toInstance(mFlightNumber);
    }

}

