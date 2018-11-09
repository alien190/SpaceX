package com.example.ivanovnv.spacex.di.launchSearchFilter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PagerSnapHelper;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.customComponents.LaunchLayoutManager;
import com.example.ivanovnv.spacex.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.launchDetail.photos.PhotosListAdapter;
import com.example.ivanovnv.spacex.launchSearchFilter.LaunchSearchFilterViewModel;

import toothpick.config.Module;

public class LaunchSearchFilterFragmentModule extends Module {

    private Fragment mFragment;
    private LaunchSearchFilter mLaunchSearchFilter;

    public LaunchSearchFilterFragmentModule(Fragment fragment, LaunchSearchFilter launchSearchFilter) {
        mFragment = fragment;
        mLaunchSearchFilter = launchSearchFilter;
        bind(LaunchSearchFilterViewModel.class).toProvider(LaunchSearchFilterViewModelProvider.class).providesSingletonInScope();
        bind(LaunchSearchFilterViewModelFactory.class).toProvider(LaunchSearchFilterViewModelFactoryProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(LaunchSearchFilter.class).toInstance(mLaunchSearchFilter);
        bind(LaunchAdapter.class).to(LaunchAdapter.class);
        bind(LaunchLayoutManager.class).to(LaunchLayoutManager.class);
    }

}

