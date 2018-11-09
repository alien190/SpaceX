package com.example.ivanovnv.spacex.di.launchSearchFilter;

import android.support.v4.app.Fragment;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.customComponents.SearchFilterLayoutManager;
import com.example.ivanovnv.spacex.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.launchSearch.SearchFilterAdapter;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterViewModel;
import com.example.ivanovnv.spacex.launchSearchFilter.LaunchSearchFilterViewModel;

import toothpick.config.Module;

public class LaunchSearchFilterFragmentModule extends Module {

    private Fragment mFragment;
    private LaunchSearchFilter mLaunchSearchFilter;

    public LaunchSearchFilterFragmentModule(Fragment fragment, LaunchSearchFilter launchSearchFilter) {
        mFragment = fragment;
        mLaunchSearchFilter = launchSearchFilter;
        bind(ILaunchSearchFilterViewModel.class).toProvider(LaunchSearchFilterViewModelProvider.class).providesSingletonInScope();
        bind(LaunchSearchFilterViewModelFactory.class).toProvider(LaunchSearchFilterViewModelFactoryProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(LaunchSearchFilter.class).toInstance(mLaunchSearchFilter);
        bind(SearchFilterAdapter.class).to(SearchFilterAdapter.class);
        bind(SearchFilterLayoutManager.class).to(SearchFilterLayoutManager.class);
    }

}
