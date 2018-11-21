package com.example.ivanovnv.spacex.ui.launchAnalytics;

import android.arch.lifecycle.MutableLiveData;

import com.example.domain.model.analytics.DomainAnalytics;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;


public interface ILaunchAnalyticsViewModel {

    MutableLiveData<DomainAnalytics> getBarAnalytics();

    MutableLiveData<DomainAnalytics> getPieAnalytics();

    ICurrentPreferences getCurrentPreferences();

}
