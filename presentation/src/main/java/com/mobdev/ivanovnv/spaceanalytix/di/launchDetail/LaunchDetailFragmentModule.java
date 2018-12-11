package com.mobdev.ivanovnv.spaceanalytix.di.launchDetail;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import com.mobdev.ivanovnv.spaceanalytix.ui.launchDetail.LaunchDetailViewModel;
import com.mobdev.ivanovnv.spaceanalytix.ui.launchDetail.photos.PhotosListAdapter;
import com.mobdev.ivanovnv.spaceanalytix.customComponents.LaunchLayoutManager;

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
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(Integer.class).withName(FLIGHT_NUMBER_NAME).toInstance(mFlightNumber);
        bind(GridLayoutManager.class).toInstance(new GridLayoutManager(mFragment.getContext(), 2));
        bind(PhotosListAdapter.class).toInstance(new PhotosListAdapter());
        bind(PagerSnapHelper.class).toInstance(new PagerSnapHelper());
    }

}

