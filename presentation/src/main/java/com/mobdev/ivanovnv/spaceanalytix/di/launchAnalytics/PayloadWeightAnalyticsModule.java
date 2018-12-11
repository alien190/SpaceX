package com.mobdev.ivanovnv.spaceanalytix.di.launchAnalytics;

import com.mobdev.domain.model.filter.IAnalyticsFilter;
import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.AnalyticsFilterAdapterByType;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;

import toothpick.config.Module;

public class PayloadWeightAnalyticsModule extends Module {
    public PayloadWeightAnalyticsModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(
                new AnalyticsFilterAdapterByType(launchService, IAnalyticsFilter.ItemType.PAYLOAD_WEIGHT));
    }
}
