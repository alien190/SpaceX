package com.example.ivanovnv.spacex.ui.launchAnalytics;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilterItem;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class LaunchAnalyticsViewModel extends ViewModel implements ILaunchAnalyticsViewModel {
    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<DomainAnalytics> mBarAnalytics = new MutableLiveData<>();
    private MutableLiveData<DomainAnalytics> mPieAnalytics = new MutableLiveData<>();

    public LaunchAnalyticsViewModel(ILaunchService launchService,
                                    ICurrentPreferences currentPreferences) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mCompositeDisposable.add(
                mLaunchService.getAnalyticsLive()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::setAnalytics, Timber::d)
        );
    }

    private void setAnalytics(DomainAnalytics domainAnalyticsList) {
        IAnalyticsFilter analyticsFilter = mLaunchService.getAnalyticsFilter().getSelectedFilter();
        if (analyticsFilter.getItemsCount() == 1) {
            if (analyticsFilter.getItem(0).getBaseType() == IAnalyticsFilterItem.BaseType.YEARS) {
                mBarAnalytics.postValue(domainAnalyticsList);
                mPieAnalytics.postValue(null);
            } else {
                mBarAnalytics.postValue(null);
                mPieAnalytics.postValue(domainAnalyticsList);
            }
        } else {
            mBarAnalytics.postValue(null);
            mPieAnalytics.postValue(null);
        }
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        mCompositeDisposable.clear();
        mCompositeDisposable = null;
        super.onCleared();
    }

    public MutableLiveData<DomainAnalytics> getBarAnalytics() {
        return mBarAnalytics;
    }

    @Override
    public MutableLiveData<DomainAnalytics> getPieAnalytics() {
        return mPieAnalytics;
    }

    public ICurrentPreferences getCurrentPreferences() {
        return mCurrentPreferences;
    }

}
