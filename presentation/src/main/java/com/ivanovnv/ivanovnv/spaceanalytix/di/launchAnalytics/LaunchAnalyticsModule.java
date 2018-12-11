package com.ivanovnv.ivanovnv.spaceanalytix.di.launchAnalytics;

import androidx.fragment.app.FragmentActivity;

import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.AnalyticsFilterAdapterBySelected;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.ivanovnv.ivanovnv.spaceanalytix.ui.launchAnalytics.ILaunchAnalyticsViewModel;

import toothpick.config.Module;

public class LaunchAnalyticsModule extends Module {
    public LaunchAnalyticsModule(FragmentActivity activity, ILaunchService launchService) {
        bind(FragmentActivity.class).toInstance(activity);
        bind(BaseFilterAdapter.class).toInstance(new AnalyticsFilterAdapterBySelected(launchService));
        bind(ILaunchAnalyticsViewModel.class).toProvider(LaunchAnalyticsViewModelProvider.class);
        bind(LaunchAnalyticsViewModelFactory.class).toProvider(LaunchAnalyticsViewModelFactoryProvider.class);
    }
}
