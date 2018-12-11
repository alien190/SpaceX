package com.mobdev.ivanovnv.spaceanalytix.ui.launchAnalytics;

import androidx.lifecycle.MutableLiveData;

import com.mobdev.domain.model.analytics.DomainAnalytics;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;


public interface ILaunchAnalyticsViewModel {

    MutableLiveData<DomainAnalytics> getBarAnalytics();

    MutableLiveData<DomainAnalytics> getPieAnalytics();

    ICurrentPreferences getCurrentPreferences();

}
