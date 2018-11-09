package com.example.ivanovnv.spacex.launchSearchFilter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.domain.service.ILaunchService;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchSearchFilterViewModel extends ViewModel implements ILaunchSearchFilterViewModel {
    private ILaunchService mLaunchService;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<LaunchSearchFilter>> mListRocketNames = new MutableLiveData<>();

    public LaunchSearchFilterViewModel(ILaunchService launchService, LaunchSearchFilter launchSearchFilter) {
        mLaunchService = launchService;
        initListRocketNames();
    }

    private void initListRocketNames() {
        mCompositeDisposable.add(
                mLaunchService.getRocketNamesFilterList()
                        .observeOn(Schedulers.io())
                        .subscribe(mListRocketNames::postValue, Timber::d));
    }

    @Override
    public MutableLiveData<List<LaunchSearchFilter>> getListRocketNames() {
        return mListRocketNames;
    }

    @Override
    public void onFilterItemClick(LaunchSearchFilter item) {
        item.setSelected(!item.isSelected());
    }
}
