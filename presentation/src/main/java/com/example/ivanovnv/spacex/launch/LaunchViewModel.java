package com.example.ivanovnv.spacex.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.launchSearch.ILaunchSearchViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchViewModel extends ViewModel
        implements ILaunchListViewModel, ILaunchSearchViewModel {

    private MutableLiveData<Boolean> mIsLoadData = new MutableLiveData<>();
    private MutableLiveData<SwipeRefreshLayout.OnRefreshListener> mOnRefreshListener = new MutableLiveData<>();
    private MutableLiveData<List<DomainLaunch>> mLaunches = new MutableLiveData<>();
    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;
    private ISearchFilter mSearchFilter;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Disposable mLaunchLiveDisposable;


    public LaunchViewModel(ILaunchService launchService, ICurrentPreferences currentPreferences) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mSearchFilter = mLaunchService.getSearchFilter();
        mCompositeDisposable.add(mSearchFilter.getUpdatesLive()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadLaunches, Timber::d));
        mOnRefreshListener.postValue(this::refreshLaunches);
        loadLaunches(mSearchFilter);
    }


    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        mCompositeDisposable.clear();
        mIsLoadData.postValue(false);
        super.onCleared();
    }


    private void refreshLaunches() {
        mCompositeDisposable.add(mLaunchService.refreshLaunches()
                .doOnSubscribe(s -> mIsLoadData.postValue(true))
                .doOnComplete(() -> mIsLoadData.postValue(false))
                .doOnError(throwable -> {
                    mIsLoadData.postValue(false);
                    Timber.d(throwable);
                })
                .subscribe(b -> mIsLoadData.postValue(false), Timber::d));
    }


    @Override
    public void submitTextQuery(String s) {
        mSearchFilter.submitTextQuery(s);
    }

    @Override
    public void changeTextQuery(String s) {
        mSearchFilter.setTextQuery(s);
    }

    private void loadLaunches(ISearchFilter searchFilter) {
        if (mLaunchLiveDisposable != null) {
            mLaunchLiveDisposable.dispose();
        }

        mLaunchLiveDisposable = mLaunchService.getLaunchesLiveWithFilter(searchFilter)
                .observeOn(Schedulers.io())
                .subscribe(mLaunches::postValue, Timber::d);
    }

    public MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public MutableLiveData<Boolean> getIsLoadInProgress() {
        return mIsLoadData;
    }

    public MutableLiveData<List<DomainLaunch>> getLaunches() {
        return mLaunches;
    }
}
