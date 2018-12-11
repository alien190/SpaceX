package com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter;

import com.ivanovnv.domain.model.filter.ISearchFilter;
import com.ivanovnv.domain.service.ILaunchService;

import javax.inject.Inject;

public class SearchFilterAdapterByOrbit extends ChoiceSearchFilterAdapter
        implements BaseFilterAdapter.IOnFilterItemClickListener {
    @Inject
    public SearchFilterAdapterByOrbit(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter) {
        return searchFilter.getFilterByType(ISearchFilter.ItemType.BY_ORBIT);
    }
}
