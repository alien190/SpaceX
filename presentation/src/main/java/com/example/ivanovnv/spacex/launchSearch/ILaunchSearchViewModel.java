package com.example.ivanovnv.spacex.launchSearch;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.SearchView;

import com.example.domain.model.searchFilter.LaunchSearchFilter;

import java.util.List;

public interface ILaunchSearchViewModel extends
        SearchView.OnQueryTextListener,
        SearchFilterAdapter.IOnFilterItemRemoveCallback,
        SearchFilterAdapter.IOnFilterItemClickListener {

    MutableLiveData<List<LaunchSearchFilter>> getSearchFilter();

    MutableLiveData<String> getSearchByNameQuery();
}
