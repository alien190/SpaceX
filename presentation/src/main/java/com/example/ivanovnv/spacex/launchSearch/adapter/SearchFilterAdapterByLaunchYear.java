package com.example.ivanovnv.spacex.launchSearch.adapter;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.service.ILaunchService;

import javax.inject.Inject;

public class SearchFilterAdapterByLaunchYear extends ChoiceSearchFilterAdapter
        implements BaseSearchFilterAdapter.IOnFilterItemClickListener {
    @Inject
    public SearchFilterAdapterByLaunchYear(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter) {
        return searchFilter.getFilterByType(ISearchFilter.ItemType.BY_LAUNCH_YEAR);
    }
}
