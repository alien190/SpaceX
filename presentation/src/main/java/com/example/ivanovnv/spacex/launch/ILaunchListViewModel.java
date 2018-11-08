package com.example.ivanovnv.spacex.launch;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;

import com.example.domain.model.launch.DomainLaunch;

import java.util.List;

public interface ILaunchListViewModel extends SearchView.OnQueryTextListener {
    MutableLiveData<List<DomainLaunch>> getLaunches();
    MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener();
    MutableLiveData<Boolean> getIsLoadInProgress();
}
