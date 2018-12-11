package com.mobdev.ivanovnv.spaceanalytix.di.launchAnalytics;

import com.mobdev.domain.model.filter.IAnalyticsFilter;
import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.AnalyticsFilterAdapterByType;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;

import toothpick.config.Module;

public class LaunchCountAnalyticsModule extends Module {
    public LaunchCountAnalyticsModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(
                new AnalyticsFilterAdapterByType(launchService, IAnalyticsFilter.ItemType.LAUNCH_COUNT));
    }
}
