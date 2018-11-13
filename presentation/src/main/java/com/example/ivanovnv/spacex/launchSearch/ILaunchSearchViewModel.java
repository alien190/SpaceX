package com.example.ivanovnv.spacex.launchSearch;

import android.arch.lifecycle.MutableLiveData;
import android.support.v7.widget.SearchView;

import com.example.ivanovnv.spacex.launchSearch.adapter.BaseSearchFilterAdapter;

import java.util.List;

public interface ILaunchSearchViewModel extends
        SearchView.OnQueryTextListener,
        BaseSearchFilterAdapter.IOnFilterItemClickListener {

    MutableLiveData<String> getSearchByNameQuery();

    void submitTextSearch();
}
