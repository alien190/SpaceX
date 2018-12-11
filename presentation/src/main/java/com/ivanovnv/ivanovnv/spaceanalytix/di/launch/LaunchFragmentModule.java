package com.ivanovnv.ivanovnv.spaceanalytix.di.launch;

import androidx.fragment.app.FragmentActivity;

import com.ivanovnv.ivanovnv.spaceanalytix.ui.launch.ILaunchListViewModel;
import com.ivanovnv.ivanovnv.spaceanalytix.ui.launch.LaunchAdapter;
import com.ivanovnv.ivanovnv.spaceanalytix.customComponents.LaunchLayoutManager;
import com.ivanovnv.ivanovnv.spaceanalytix.ui.launchSearch.ILaunchSearchViewModel;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByLaunchYear;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByRocketName;

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

