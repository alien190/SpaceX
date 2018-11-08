package com.example.ivanovnv.spacex.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.service.ILaunchService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchViewModel extends ViewModel implements ILaunchListViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mIsLoadData = new MutableLiveData<>();
    private MutableLiveData<SwipeRefreshLayout.OnRefreshListener> mOnRefreshListener = new MutableLiveData<>();
    private ILaunchService mLaunchService;
    private MutableLiveData<List<DomainLaunch>> mLaunches = new MutableLiveData<>();
    private String mSearchByNameQuery = "";


    public LaunchViewModel(ILaunchService launchService) {
        mLaunchService = launchService;
        mOnRefreshListener.postValue(this::refreshLaunches);
        loadLaunches();
    }

    private void loadLaunches() {
        compositeDisposable.add(mLaunchService.getLaunchesLiveWithFilter(mSearchByNameQuery)
                .observeOn(Schedulers.io())
                .subscribe(mLaunches::postValue, Timber::d));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        mIsLoadData.postValue(false);
        super.onCleared();
    }

    private void refreshLaunches() {
        compositeDisposable.add(mLaunchService.refreshLaunches()
                .doOnSubscribe(s -> mIsLoadData.postValue(true))
                .doOnComplete(() -> mIsLoadData.postValue(false))
                .doOnError(throwable -> {
                    mIsLoadData.postValue(false);
                    Timber.d(throwable);
                })
                .subscribe(b -> mIsLoadData.postValue(false), Timber::d));
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

    private void setSearchByNameQuery(String query) {
        mSearchByNameQuery = query;
        loadLaunches();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        setSearchByNameQuery(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        setSearchByNameQuery(s);
        return true;
    }
}
