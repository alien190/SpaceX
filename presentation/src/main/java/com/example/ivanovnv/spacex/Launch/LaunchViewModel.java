package com.example.ivanovnv.spacex.Launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.service.ILaunchService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class LaunchViewModel extends ViewModel {
    //private MutableLiveData<LaunchAdapter> mLaunchAdapter = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<Boolean> mIsLoadData = new MutableLiveData<>();
    private MutableLiveData<SwipeRefreshLayout.OnRefreshListener> mOnRefreshListener = new MutableLiveData<>();
    private ILaunchService mLaunchService;
    private MutableLiveData<List<DomainLaunch>> mLaunches = new MutableLiveData<>();


    public LaunchViewModel(ILaunchService launchService) {

        mLaunchService = launchService;

        //LaunchAdapter launchAdapter = new LaunchAdapter();

        mOnRefreshListener.postValue(this::loadLaunches);
        //  mLaunchAdapter.postValue(launchAdapter);
        compositeDisposable.add(launchService.getLaunchesLive()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mLaunches::postValue));

        // loadLaunches();
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

//    public MutableLiveData<LaunchAdapter> getLaunchAdapter() {
//        return mLaunchAdapter;
//    }

    private void loadLaunches() {
        mIsLoadData.postValue(true);
        compositeDisposable.add(mLaunchService.refreshLaunches()
                .doOnComplete(() -> mIsLoadData.postValue(false))
                .subscribe(b -> mIsLoadData.postValue(false), Timber::d));
    }

    public MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public MutableLiveData<Boolean> getIsLoadData() {
        return mIsLoadData;
    }

    public MutableLiveData<List<DomainLaunch>> getLaunches() {
        return mLaunches;
    }
}
