package com.example.ivanovnv.spacex.LaunchFragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.service.LaunchService;
import com.example.domain.service.LaunchServiceImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class LaunchViewModel extends ViewModel {
    private MutableLiveData<LaunchAdapter> mLaunchAdapter = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mIsLoadData = new MutableLiveData<>();
    private MutableLiveData<SwipeRefreshLayout.OnRefreshListener> mOnRefreshListener = new MutableLiveData<>();
    private LaunchService mLaunchService;


    public LaunchViewModel(LaunchService launchService) {

        mLaunchService = launchService;

        LaunchAdapter launchAdapter = new LaunchAdapter();

        mOnRefreshListener.postValue(this::loadLaunches);
        mLaunchAdapter.postValue(launchAdapter);
        compositeDisposable.add(launchService.getLaunchesLive()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(launchAdapter::updateLaunches));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public MutableLiveData<LaunchAdapter> getLaunchAdapter() {
        return mLaunchAdapter;
    }

    private void loadLaunches() {
        mIsLoadData.postValue(true);
        compositeDisposable.add(mLaunchService.refreshLaunches()
                .subscribe(b -> mIsLoadData.postValue(false)));
    }

    public MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public MutableLiveData<Boolean> getIsLoadData() {
        return mIsLoadData;
    }
}
