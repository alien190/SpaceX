package com.example.ivanovnv.spacex.launchSearchFilter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.domain.model.searchFilter.SearchFilterItem;
import com.example.domain.model.searchFilter.SearchFilterItemType;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchSearchFilterViewModel extends ViewModel implements ILaunchSearchFilterViewModel {
    private ILaunchService mLaunchService;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<SearchFilterItem>> mListRocketNames = new MutableLiveData<>();
    private MutableLiveData<List<SearchFilterItem>> mListLaunchYears = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanChoice = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCloseDialog = new MutableLiveData<>();
    private SearchFilterItem mLaunchSearchFilterForEdit;
    private ILaunchSearchFilterCallback mCallback;
    private ICurrentPreferences mCurrentPreferences;

    public LaunchSearchFilterViewModel(ILaunchService launchService,
                                       ICurrentPreferences currentPreferences,
                                       ILaunchSearchFilterCallback callback) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mCallback = callback;
    }

    @Override
    public void initLists() {
        mCompositeDisposable.addAll(
                mLaunchService.getRocketNamesFilterList()
                        .observeOn(Schedulers.io())
                        .subscribe(mListRocketNames::postValue, Timber::d),
                mLaunchService.getLaunchYearsFilterList()
                        .observeOn(Schedulers.io())
                        .subscribe(mListLaunchYears::postValue, Timber::d)
        );
    }

    @Override
    public MutableLiveData<List<SearchFilterItem>> getListRocketNames() {
        return mListRocketNames;
    }

    @Override
    public MutableLiveData<List<SearchFilterItem>> getListLaunchYears() {
        return mListLaunchYears;
    }

    @Override
    public void onFilterItemClick(SearchFilterItem item) {
        Boolean canChoice = mCanChoice.getValue();
        if (canChoice != null) {
            if (canChoice) {
                item.setSelected(!item.isSelected());
            } else {
                if (mCallback != null) {
                    List<SearchFilterItem> list = new ArrayList<>();
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

    private List<SearchFilterItem> getSelectedFilterItemsOverAll() {
        List<SearchFilterItem> retList = new ArrayList<>();
        retList.addAll(getSelectedFilterItemsInList(mListRocketNames.getValue()));
        retList.addAll(getSelectedFilterItemsInList(mListLaunchYears.getValue()));
        return retList;
    }

    private List<SearchFilterItem> getSelectedFilterItemsInList(@Nullable List<SearchFilterItem> list) {
        List<SearchFilterItem> retList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (SearchFilterItem item : list) {
                if (item.isSelected()) {
                    retList.add(item);
                }
            }
        }
        return retList;
    }

    @Override
    public void setLaunchSearchFilterForEdit(SearchFilterItem launchSearchFilterForEdit) {
        mLaunchSearchFilterForEdit = launchSearchFilterForEdit;
        mCanChoice.postValue(mLaunchSearchFilterForEdit.getType() == SearchFilterItemType.EMPTY);
    }

    public MutableLiveData<Boolean> getCloseDialog() {
        return mCloseDialog;
    }

    @Override
    public void onFilterItemCloseClick(SearchFilterItem item) {
        //do nothing
    }
}
