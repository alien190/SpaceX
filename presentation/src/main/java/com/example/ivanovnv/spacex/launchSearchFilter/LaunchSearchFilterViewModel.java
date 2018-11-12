package com.example.ivanovnv.spacex.launchSearchFilter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.domain.model.searchFilter.LaunchSearchType;
import com.example.domain.service.ILaunchService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchSearchFilterViewModel extends ViewModel implements ILaunchSearchFilterViewModel {
    private ILaunchService mLaunchService;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<LaunchSearchFilter>> mListRocketNames = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanChoice = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCloseDialog = new MutableLiveData<>();
    private LaunchSearchFilter mLaunchSearchFilterForEdit;
    private ILaunchSearchFilterCallback mCallback;

    public LaunchSearchFilterViewModel(ILaunchService launchService,
                                       ILaunchSearchFilterCallback callback) {
        mLaunchService = launchService;
        mCallback = callback;
    }

    @Override
    public void initListRocketNames() {
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
        Boolean canChoice = mCanChoice.getValue();
        if (canChoice != null) {
            if (canChoice) {
                item.setSelected(!item.isSelected());
            } else {
                if (mCallback != null) {
                    List<LaunchSearchFilter> list = new ArrayList<>();
                    list.add(item);
                    mCallback.onFilterEditFinished(mLaunchSearchFilterForEdit, list);
                }
                mCloseDialog.postValue(true);
            }
        }
    }

    public MutableLiveData<Boolean> getCanChoice() {
        return mCanChoice;
    }

    @Override
    public void onOkButtonClick() {
        if (mCallback != null) {
            mCallback.onFilterEditFinished(mLaunchSearchFilterForEdit, getSelectedFilterItemsOverAll());
        }
        mCloseDialog.postValue(true);
    }

    private List<LaunchSearchFilter> getSelectedFilterItemsOverAll() {
        return getSelectedFilterItemsInList(mListRocketNames.getValue());
    }

    private List<LaunchSearchFilter> getSelectedFilterItemsInList(@Nullable List<LaunchSearchFilter> list) {
        List<LaunchSearchFilter> retList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (LaunchSearchFilter item : list) {
                if (item.isSelected()) {
                    retList.add(item);
                }
            }
        }
        return retList;
    }

    @Override
    public void setLaunchSearchFilterForEdit(LaunchSearchFilter launchSearchFilterForEdit) {
        mLaunchSearchFilterForEdit = launchSearchFilterForEdit;
        mCanChoice.postValue(mLaunchSearchFilterForEdit.getType() == LaunchSearchType.EMPTY);
    }

    public MutableLiveData<Boolean> getCloseDialog() {
        return mCloseDialog;
    }

    @Override
    public void onFilterItemCloseClick(LaunchSearchFilter item) {
        //do nothing
    }
}
