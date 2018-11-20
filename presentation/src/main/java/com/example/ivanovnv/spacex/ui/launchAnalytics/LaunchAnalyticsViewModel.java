package com.example.ivanovnv.spacex.ui.launchAnalytics;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilterItem;
import com.example.domain.service.ILaunchService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class LaunchAnalyticsViewModel extends ViewModel implements ILaunchAnalyticsViewModel {
    private ILaunchService mLaunchService;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<DomainAnalytics>> mBarAnalytics = new MutableLiveData<>();
    private MutableLiveData<List<DomainAnalytics>> mPieAnalytics = new MutableLiveData<>();

    public LaunchAnalyticsViewModel(ILaunchService launchService) {
        mLaunchService = launchService;
        mCompositeDisposable.add(
                mLaunchService.getAnalyticsLive()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setAnalytics, Timber::d)
        );
    }

    private void setAnalytics(List<DomainAnalytics> domainAnalyticsList) {
        IAnalyticsFilter analyticsFilter = mLaunchService.getAnalyticsFilter().getSelectedFilter();
        if (analyticsFilter.getItemsCount() == 1) {
            if (analyticsFilter.getItem(0).getBaseType() == IAnalyticsFilterItem.BaseType.YEARS) {
                mBarAnalytics.postValue(domainAnalyticsList);
                mPieAnalytics.postValue(new ArrayList<>());
            } else {
                mBarAnalytics.postValue(new ArrayList<>());
                mPieAnalytics.postValue(domainAnalyticsList);
            }
        } else {
            mBarAnalytics.postValue(new ArrayList<>());
            mPieAnalytics.postValue(new ArrayList<>());
        }
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        mCompositeDisposable.clear();
        mCompositeDisposable = null;
        super.onCleared();
    }

    public MutableLiveData<List<DomainAnalytics>> getBarAnalytics() {
        return mBarAnalytics;
    }

    @Override
    public MutableLiveData<List<DomainAnalytics>> getPieAnalytics() {
        return mPieAnalytics;
    }
}
