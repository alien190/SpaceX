package com.example.ivanovnv.spacex.launchSearchFilter;

import android.arch.lifecycle.MutableLiveData;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.ivanovnv.spacex.launchSearch.adapter.BaseSearchFilterAdapter;

public interface ILaunchSearchFilterViewModel extends BaseSearchFilterAdapter.IOnFilterItemClickListener {
    MutableLiveData<ISearchFilter> getListRocketNames();

    MutableLiveData<ISearchFilter> getListLaunchYears();

    MutableLiveData<Boolean> getCanChoice();

    MutableLiveData<Boolean> getCloseDialog();

    void onOkButtonClick();

    void initLists();

}
