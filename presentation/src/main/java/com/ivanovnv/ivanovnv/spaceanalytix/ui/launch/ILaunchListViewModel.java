package com.ivanovnv.ivanovnv.spaceanalytix.ui.launch;

import androidx.lifecycle.MutableLiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ivanovnv.domain.model.launch.DomainLaunch;

import java.util.List;

public interface ILaunchListViewModel {
    MutableLiveData<List<DomainLaunch>> getLaunches();
    MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener();
    MutableLiveData<Boolean> getIsLoadInProgress();
}
