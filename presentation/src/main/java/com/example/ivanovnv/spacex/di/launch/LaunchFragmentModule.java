package com.example.ivanovnv.spacex.di.launch;

import android.support.v4.app.FragmentActivity;

import com.example.ivanovnv.spacex.customComponents.FilterLayoutManager;
import com.example.ivanovnv.spacex.ui.launch.ILaunchListViewModel;
import com.example.ivanovnv.spacex.ui.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.customComponents.LaunchLayoutManager;
import com.example.ivanovnv.spacex.ui.launchSearch.ILaunchSearchViewModel;
import com.example.ivanovnv.spacex.filterAdapter.SearchFilterAdapterByLaunchYear;
import com.example.ivanovnv.spacex.filterAdapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class LaunchFragmentModule extends Module {

    public LaunchFragmentModule(FragmentActivity activity) {
        bind(ILaunchListViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(ILaunchSearchViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(FragmentActivity.class).toInstance(activity);
        bind(LaunchAdapter.class).toProvider(LaunchAdapterProvider.class);
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(LaunchViewModelFactory.class).toProvider(LaunchViewModelFactoryProvider.class);
        bind(SearchFilterAdapterByRocketName.class).to(SearchFilterAdapterByRocketName.class);
        bind(SearchFilterAdapterByLaunchYear.class).to(SearchFilterAdapterByLaunchYear.class);
    }
}

