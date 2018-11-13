package com.example.ivanovnv.spacex.launchSearch.adapter;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.SearchFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

import javax.inject.Inject;

public class ByRocketNameSearchFilterAdapter extends BaseSearchFilterAdapter
        implements BaseSearchFilterAdapter.IOnFilterItemClickListener {
    @Inject
    public ByRocketNameSearchFilterAdapter(ILaunchService launchService) {
        super(launchService);
        setOnFilterItemClickListener(this);
    }

    @Override
    ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter) {
        //return searchFilter;
        return searchFilter.getFilterByType(ISearchFilter.ItemType.BY_ROCKET_NAME);
    }

    @Override
    int getViewHolderLayoutResId() {
        return R.layout.li_search_filter_choice;
    }

    @Override
    public void onFilterItemClick(SearchFilter.SearchFilterItem item) {
        mLaunchService.getSearchFilter().switchItemSelectedState(item);
    }
}
