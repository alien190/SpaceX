package com.example.ivanovnv.spaceanalytix.ui.launchAnalytics;

import androidx.lifecycle.MutableLiveData;

import com.example.domain.model.analytics.DomainAnalytics;
import com.example.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;


public interface ILaunchAnalyticsViewModel {

    MutableLiveData<DomainAnalytics> getBarAnalytics();

    MutableLiveData<DomainAnalytics> getPieAnalytics();

    ICurrentPreferences getCurrentPreferences();

}
