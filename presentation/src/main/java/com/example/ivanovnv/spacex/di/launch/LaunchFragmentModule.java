package com.example.ivanovnv.spacex.di.launch;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.ivanovnv.spacex.customComponents.SearchFilterLayoutManager;
import com.example.ivanovnv.spacex.launch.ILaunchListViewModel;
import com.example.ivanovnv.spacex.launch.LaunchAdapter;
import com.example.ivanovnv.spacex.customComponents.LaunchLayoutManager;
import com.example.ivanovnv.spacex.launchSearch.ILaunchSearchViewModel;
import com.example.ivanovnv.spacex.launchSearch.SearchFilterAdapterBase;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterCallback;

import toothpick.config.Module;

public class LaunchFragmentModule extends Module {
    //private SearchFilterAdapterBase mSearchFilterListAdapter;

    public LaunchFragmentModule(FragmentActivity activity) {
        //mSearchFilterListAdapter = new SearchFilterAdapterBase();

        bind(ILaunchListViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(ILaunchSearchViewModel.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(ILaunchSearchFilterCallback.class).toProvider(LaunchViewModelProvider.class).providesSingletonInScope();
        bind(FragmentActivity.class).toInstance(activity);
        bind(LaunchAdapter.class).toInstance(new LaunchAdapter());
        bind(LaunchLayoutManager.class).toInstance(new LaunchLayoutManager());
        bind(LaunchViewModelFactory.class).toProvider(LaunchViewModelFactoryProvider.class);
        bind(SearchFilterLayoutManager.class).toInstance(new SearchFilterLayoutManager());
        bind(SearchFilterAdapterBase.class).to(SearchFilterAdapterBase.class);
        //bind(ItemTouchAdapter.class).to(SearchFilterAdapterBase.class).singletonInScope();
        bind(ItemTouchHelper.Callback.class).toProvider(ItemTouchCallbackProvider.class).providesSingletonInScope();
        bind(ItemTouchHelper.class).toProvider(ItemTouchHelperProvider.class).providesSingletonInScope();
    }

}

