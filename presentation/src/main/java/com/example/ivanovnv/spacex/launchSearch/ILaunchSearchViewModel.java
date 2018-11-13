package com.example.ivanovnv.spacex.launchSearch;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.SearchView;

import com.example.domain.model.searchFilter.SearchFilterItem;

import java.util.List;

public interface ILaunchSearchViewModel extends
        SearchView.OnQueryTextListener,
        SearchFilterAdapterBase.IOnFilterItemRemoveCallback,
        SearchFilterAdapterBase.IOnFilterItemClickListener {

    MutableLiveData<List<SearchFilterItem>> getSearchFilterLive();

    MutableLiveData<String> getSearchByNameQuery();

    MutableLiveData<SearchFilterItem> getSearchFilterItemForEdit();

    void submitTextSearch();
}
