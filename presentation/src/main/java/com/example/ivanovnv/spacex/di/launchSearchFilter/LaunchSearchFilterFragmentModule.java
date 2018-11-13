package com.example.ivanovnv.spacex.di.launchSearchFilter;

import android.support.v4.app.Fragment;

import com.example.domain.model.searchFilter.SearchFilterItem;
import com.example.ivanovnv.spacex.customComponents.SearchFilterLayoutManager;
import com.example.ivanovnv.spacex.launchSearch.SearchFilterAdapterBase;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterViewModel;

import toothpick.config.Module;

public class LaunchSearchFilterFragmentModule extends Module {

    private Fragment mFragment;
    private SearchFilterItem mLaunchSearchFilter;

    public LaunchSearchFilterFragmentModule(Fragment fragment, SearchFilterItem launchSearchFilter) {
        mFragment = fragment;
        mLaunchSearchFilter = launchSearchFilter;
        bind(ILaunchSearchFilterViewModel.class).toProvider(LaunchSearchFilterViewModelProvider.class).providesSingletonInScope();
        bind(LaunchSearchFilterViewModelFactory.class).toProvider(LaunchSearchFilterViewModelFactoryProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(SearchFilterItem.class).toInstance(mLaunchSearchFilter);
        bind(SearchFilterAdapterBase.class).to(SearchFilterAdapterBase.class);
        bind(SearchFilterLayoutManager.class).to(SearchFilterLayoutManager.class);
    }

}

