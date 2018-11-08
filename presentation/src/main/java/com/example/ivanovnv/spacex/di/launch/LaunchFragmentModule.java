package com.example.ivanovnv.spacex.di.launch;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.example.ivanovnv.spacex.launch.ILaunchListViewModel;
import com.example.ivanovnv.spacex.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.customComponents.LaunchLayoutManager;
import com.example.ivanovnv.spacex.launchSearch.ILaunchSearchViewModel;
import com.example.ivanovnv.spacex.launchSearch.SearchFilterListAdapter;

import toothpick.config.Module;

public class LaunchFragmentModule extends Module {
    private Fragment mFragment;

    public LaunchFragmentModule(Fragment fragment) {
        mFragment = fragment;
        bind(ILaunchListViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(ILaunchSearchViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(Fragment.class).toInstance(mFragment);
        bind(LaunchAdapter.class).toInstance(new LaunchAdapter());
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(LaunchViewModelFactory.class).toProvider(LaunchViewModelFactoryProvider.class);
        bind(LinearLayoutManager.class).toInstance(new LinearLayoutManager(mFragment.getContext()));
        bind(SearchFilterListAdapter.class).toInstance(new SearchFilterListAdapter());
    }

}

