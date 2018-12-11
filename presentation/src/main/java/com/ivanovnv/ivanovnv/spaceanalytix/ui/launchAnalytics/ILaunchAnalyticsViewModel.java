package com.ivanovnv.ivanovnv.spaceanalytix.ui.launchAnalytics;

import androidx.lifecycle.MutableLiveData;

import com.ivanovnv.domain.model.analytics.DomainAnalytics;
import com.ivanovnv.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;


public interface ILaunchAnalyticsViewModel {

    MutableLiveData<DomainAnalytics> getBarAnalytics();

    MutableLiveData<DomainAnalytics> getPieAnalytics();

    ICurrentPreferences getCurrentPreferences();

}
