package com.example.ivanovnv.spacex.ui.launchAnalytics;

import android.arch.lifecycle.MutableLiveData;

import com.example.domain.model.analytics.DomainAnalytics;

import java.util.List;

public interface ILaunchAnalyticsViewModel {

    MutableLiveData<List<DomainAnalytics>> getBarAnalytics();

    MutableLiveData<List<DomainAnalytics>> getPieAnalytics();

}
