package com.example.ivanovnv.spacex.di.launchAnalytics;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.filterAdapter.AnalyticsFilterAdapterBySelected;
import com.example.ivanovnv.spacex.filterAdapter.BaseFilterAdapter;

import toothpick.config.Module;

public class LaunchAnalyticsModule extends Module {
    public LaunchAnalyticsModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new AnalyticsFilterAdapterBySelected(launchService));
    }
}
