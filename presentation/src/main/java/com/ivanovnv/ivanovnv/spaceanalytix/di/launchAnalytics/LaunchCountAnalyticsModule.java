package com.ivanovnv.ivanovnv.spaceanalytix.di.launchAnalytics;

import com.ivanovnv.domain.model.filter.IAnalyticsFilter;
import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.AnalyticsFilterAdapterByType;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;

import toothpick.config.Module;

public class LaunchCountAnalyticsModule extends Module {
    public LaunchCountAnalyticsModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(
                new AnalyticsFilterAdapterByType(launchService, IAnalyticsFilter.ItemType.LAUNCH_COUNT));
    }
}
