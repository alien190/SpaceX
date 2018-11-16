package com.example.ivanovnv.spacex.launchSearch.adapter;

import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

public class AnalyticsFilterAdapterSelected extends AnalyticsFilterAdapter {
    public AnalyticsFilterAdapterSelected(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    IAnalyticsFilter mapSearchFilterUpdates(IAnalyticsFilter analyticsFilter) {
        return analyticsFilter;
    }

    @Override
    int getViewHolderLayoutResId() {
        return R.layout.li_search_filter_action;
    }
}
