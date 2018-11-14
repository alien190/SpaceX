package com.example.ivanovnv.spacex.launchSearch.adapter;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.service.ILaunchService;

import javax.inject.Inject;

public class SearchFilterAdapterByRocketName extends ChoiceSearchFilterAdapter
        implements BaseSearchFilterAdapter.IOnFilterItemClickListener {
    @Inject
    public SearchFilterAdapterByRocketName(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter) {
        return searchFilter.getFilterByType(ISearchFilter.ItemType.BY_ROCKET_NAME);
    }
}
