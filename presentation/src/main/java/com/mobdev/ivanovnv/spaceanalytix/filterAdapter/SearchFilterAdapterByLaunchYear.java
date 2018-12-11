package com.mobdev.ivanovnv.spaceanalytix.filterAdapter;

import com.mobdev.domain.model.filter.ISearchFilter;
import com.mobdev.domain.service.ILaunchService;

import javax.inject.Inject;

public class SearchFilterAdapterByLaunchYear extends ChoiceSearchFilterAdapter
        implements BaseFilterAdapter.IOnFilterItemClickListener {
    @Inject
    public SearchFilterAdapterByLaunchYear(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter) {
        return searchFilter.getFilterByType(ISearchFilter.ItemType.BY_LAUNCH_YEAR);
    }
}
