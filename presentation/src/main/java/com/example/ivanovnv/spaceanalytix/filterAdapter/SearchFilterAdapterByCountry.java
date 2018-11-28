package com.example.ivanovnv.spaceanalytix.filterAdapter;

import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.service.ILaunchService;

import javax.inject.Inject;

public class SearchFilterAdapterByCountry extends ChoiceSearchFilterAdapter
        implements BaseFilterAdapter.IOnFilterItemClickListener {
    @Inject
    public SearchFilterAdapterByCountry(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter) {
        return searchFilter.getFilterByType(ISearchFilter.ItemType.BY_COUNTRY);
    }
}
