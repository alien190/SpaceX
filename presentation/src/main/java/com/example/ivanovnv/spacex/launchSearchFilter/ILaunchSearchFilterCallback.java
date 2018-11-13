package com.example.ivanovnv.spacex.launchSearchFilter;

import com.example.domain.model.searchFilter.SearchFilterItem;

import java.util.List;

public interface ILaunchSearchFilterCallback {
    void onFilterEditFinished(SearchFilterItem oldItem, List<SearchFilterItem> newItems);
}
