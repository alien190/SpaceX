package com.example.ivanovnv.spacex.di.launchDetail;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PagerSnapHelper;

import com.example.ivanovnv.spacex.launchDetail.LaunchDetailViewModel;
import com.example.ivanovnv.spacex.launchDetail.photos.PhotosListAdapter;
import com.example.ivanovnv.spacex.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.customComponents.LaunchLayoutManager;

import toothpick.config.Module;

public class LaunchDetailFragmentModule extends Module {
    public static final String FLIGHT_NUMBER_NAME = "FLIGHT_NUMBER";
    private Fragment mFragment;
    private Integer mFlightNumber;

    public LaunchDetailFragmentModule(Fragment fragment, Integer flightNumber) {
        mFragment = fragment;
        mFlightNumber = flightNumber;
        bind(LaunchDetailViewModel.class).toProvider(LaunchDetailViewModelProvider.class).providesSingletonInScope();
        bind(LaunchDetailViewModelFactory.class).toProvider(LaunchDetailViewModelFactoryProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(LaunchAdapter.class).toInstance(new LaunchAdapter());
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(Integer.class).withName(FLIGHT_NUMBER_NAME).toInstance(mFlightNumber);
        bind(GridLayoutManager.class).toInstance(new GridLayoutManager(mFragment.getContext(), 2));
        bind(PhotosListAdapter.class).toInstance(new PhotosListAdapter());
        bind(PagerSnapHelper.class).toInstance(new PagerSnapHelper());
    }

}

