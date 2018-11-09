package com.example.ivanovnv.spacex.launchSearchFilter;

import com.example.domain.model.searchFilter.LaunchSearchFilter;

import java.util.List;

public interface ILaunchSearchFilterCallback {
    void onFilterEditFinished(LaunchSearchFilter oldItem, List<LaunchSearchFilter> newItems);
}
