package com.example.ivanovnv.spaceanalytix.ui.launch;

import android.arch.lifecycle.MutableLiveData;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.model.launch.DomainLaunch;

import java.util.List;

public interface ILaunchListViewModel {
    MutableLiveData<List<DomainLaunch>> getLaunches();
    MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener();
    MutableLiveData<Boolean> getIsLoadInProgress();
}
