package com.example.ivanovnv.spacex.di.launchSearchFilter;

import android.support.v4.app.Fragment;

import com.example.ivanovnv.spacex.customComponents.SearchFilterLayoutManager;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByLaunchYear;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByRocketName;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterViewModel;

import toothpick.config.Module;

public class LaunchSearchFilterFragmentModule extends Module {

    private Fragment mFragment;

    public LaunchSearchFilterFragmentModule(Fragment fragment) {
        mFragment = fragment;
        bind(ILaunchSearchFilterViewModel.class).toProvider(LaunchSearchFilterViewModelProvider.class).providesSingletonInScope();
        bind(LaunchSearchFilterViewModelFactory.class).toProvider(LaunchSearchFilterViewModelFactoryProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(SearchFilterAdapterByRocketName.class).to(SearchFilterAdapterByRocketName.class);
        bind(SearchFilterAdapterByLaunchYear.class).to(SearchFilterAdapterByLaunchYear.class);
        bind(SearchFilterLayoutManager.class).to(SearchFilterLayoutManager.class);
    }
}

