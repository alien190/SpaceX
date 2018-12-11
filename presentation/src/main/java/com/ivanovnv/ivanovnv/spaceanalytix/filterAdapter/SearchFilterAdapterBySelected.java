package com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter;

import com.ivanovnv.domain.model.filter.ISearchFilter;
import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.R;

import javax.inject.Inject;

public class SearchFilterAdapterBySelected extends SearchFilterAdapter {
    @Inject
    public SearchFilterAdapterBySelected(ILaunchService launchService) {
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
