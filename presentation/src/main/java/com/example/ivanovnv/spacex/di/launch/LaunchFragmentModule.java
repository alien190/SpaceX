package com.example.ivanovnv.spacex.di.launch;

import android.support.v4.app.FragmentActivity;

import com.example.ivanovnv.spacex.customComponents.SearchFilterLayoutManager;
import com.example.ivanovnv.spacex.launch.ILaunchListViewModel;
import com.example.ivanovnv.spacex.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.customComponents.LaunchLayoutManager;
import com.example.ivanovnv.spacex.launchSearch.ILaunchSearchViewModel;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByLaunchYear;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class LaunchFragmentModule extends Module {
    //private BaseSearchFilterAdapter mSearchFilterListAdapter;

    public LaunchFragmentModule(FragmentActivity activity) {
        //mSearchFilterListAdapter = new BaseSearchFilterAdapter();

        bind(ILaunchListViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(ILaunchSearchViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(FragmentActivity.class).toInstance(activity);
        bind(LaunchAdapter.class).toInstance(new LaunchAdapter());
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(LaunchViewModelFactory.class).toProvider(LaunchViewModelFactoryProvider.class);

        //bind(BaseSearchFilterAdapter.class).to(BaseSearchFilterAdapter.class);
        //bind(ItemTouchAdapter.class).to(BaseSearchFilterAdapter.class).singletonInScope();



        bind(SearchFilterAdapterByRocketName.class).to(SearchFilterAdapterByRocketName.class);
        bind(SearchFilterAdapterByLaunchYear.class).to(SearchFilterAdapterByLaunchYear.class);
        bind(SearchFilterLayoutManager.class).to(SearchFilterLayoutManager.class);
    }

}

