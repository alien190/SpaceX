package com.example.ivanovnv.spaceanalytix.di.launchAnalytics;

import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.filterAdapter.AnalyticsFilterAdapterByType;
import com.example.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;

import toothpick.config.Module;

public class LaunchCountAnalyticsModule extends Module {
    public LaunchCountAnalyticsModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(
                new AnalyticsFilterAdapterByType(launchService, IAnalyticsFilter.ItemType.LAUNCH_COUNT));
    }
}
