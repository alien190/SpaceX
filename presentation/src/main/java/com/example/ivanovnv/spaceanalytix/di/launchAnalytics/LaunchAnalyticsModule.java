package com.example.ivanovnv.spaceanalytix.di.launchAnalytics;

import android.support.v4.app.FragmentActivity;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.filterAdapter.AnalyticsFilterAdapterBySelected;
import com.example.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.example.ivanovnv.spaceanalytix.ui.launchAnalytics.ILaunchAnalyticsViewModel;

import toothpick.config.Module;

public class LaunchAnalyticsModule extends Module {
    public LaunchAnalyticsModule(FragmentActivity activity, ILaunchService launchService) {
        bind(FragmentActivity.class).toInstance(activity);
        bind(BaseFilterAdapter.class).toInstance(new AnalyticsFilterAdapterBySelected(launchService));
        bind(ILaunchAnalyticsViewModel.class).toProvider(LaunchAnalyticsViewModelProvider.class);
        bind(LaunchAnalyticsViewModelFactory.class).toProvider(LaunchAnalyticsViewModelFactoryProvider.class);
    }
}
