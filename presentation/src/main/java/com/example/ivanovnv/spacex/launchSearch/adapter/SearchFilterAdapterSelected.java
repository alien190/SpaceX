package com.example.ivanovnv.spacex.launchSearch.adapter;

import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

import javax.inject.Inject;

public class SearchFilterAdapterSelected extends SearchFilterAdapter {
    @Inject
    public SearchFilterAdapterSelected(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter) {
        return searchFilter.getSelectedFilter();
    }

    @Override
    int getViewHolderLayoutResId() {
        return R.layout.li_search_filter_action;
    }
}
