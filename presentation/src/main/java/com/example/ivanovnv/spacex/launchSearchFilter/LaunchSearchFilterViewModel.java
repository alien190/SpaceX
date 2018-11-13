package com.example.ivanovnv.spacex.launchSearchFilter;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.SearchFilter;
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
    private MutableLiveData<ISearchFilter> mListRocketNames = new MutableLiveData<>();
    private MutableLiveData<ISearchFilter> mListLaunchYears = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanChoice = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCloseDialog = new MutableLiveData<>();
    private SearchFilterItem mLaunchSearchFilterForEdit;
    private ILaunchSearchFilterCallback mCallback;
    private ICurrentPreferences mCurrentPreferences;
    private ISearchFilter mSearchFilter;

    public LaunchSearchFilterViewModel(ILaunchService launchService,
                                       ICurrentPreferences currentPreferences,
                                       ILaunchSearchFilterCallback callback) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mSearchFilter = mCurrentPreferences.getSearchFilter();
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
    public MutableLiveData<ISearchFilter> getListRocketNames() {
        return mListRocketNames;
    }

    @Override
    public MutableLiveData<ISearchFilter> getListLaunchYears() {
        return mListLaunchYears;
    }

    @Override
    public void onFilterItemClick(int index) {
//        Boolean canChoice = mCanChoice.getValue();
//        if (canChoice != null) {
//            if (canChoice) {
//                item.setSelected(!item.isSelected());
//            } else {
//                if (mCallback != null) {
//                    List<SearchFilterItem> list = new ArrayList<>();
//                    list.add(item);
//                    mCallback.onFilterEditFinished(mLaunchSearchFilterForEdit, list);
//                }
//                mCloseDialog.postValue(true);
//            }
//        }
    }

    public MutableLiveData<Boolean> getCanChoice() {
        return mCanChoice;
    }

    @Override
    public void onOkButtonClick() {

        //ISearchFilter comabinedFilter = new
//        if (mCallback != null) {
//            mCallback.onFilterEditFinished(mLaunchSearchFilterForEdit, getSelectedFilterItemsOverAll());
//        }


        mCloseDialog.postValue(true);
    }

    private ISearchFilter getSelectedFilter(MutableLiveData<ISearchFilter> filterLive) {
        ISearchFilter searchFilter = filterLive.getValue();
        if (searchFilter != null) {
            return searchFilter.getSelectedFilter();
        } else {
            return new SearchFilter();
        }
    }

//    private List<SearchFilterItem> getSelectedFilterItemsOverAll() {
//        List<SearchFilterItem> retList = new ArrayList<>();
//        retList.addAll(getSelectedFilterItemsInList(mListRocketNames.getValue()));
//        retList.addAll(getSelectedFilterItemsInList(mListLaunchYears.getValue()));
//        return retList;
//    }
//
//    private List<SearchFilterItem> getSelectedFilterItemsInList(@Nullable List<SearchFilterItem> list) {
//        List<SearchFilterItem> retList = new ArrayList<>();
//        if (list != null && !list.isEmpty()) {
//            for (SearchFilterItem item : list) {
//                if (item.isSelected()) {
//                    retList.add(item);
//                }
//            }
//        }
//        return retList;
//    }

    @Override
    public void setLaunchSearchFilterForEdit(SearchFilterItem launchSearchFilterForEdit) {
        mLaunchSearchFilterForEdit = launchSearchFilterForEdit;
        mCanChoice.postValue(mLaunchSearchFilterForEdit.getType() == SearchFilterItemType.EMPTY);
    }

    public MutableLiveData<Boolean> getCloseDialog() {
        return mCloseDialog;
    }

}
