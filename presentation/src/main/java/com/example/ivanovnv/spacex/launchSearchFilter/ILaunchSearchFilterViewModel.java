package com.example.ivanovnv.spacex.launchSearchFilter;

import android.arch.lifecycle.MutableLiveData;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.SearchFilterItem;
import com.example.ivanovnv.spacex.launchSearch.SearchFilterAdapterBase;

public interface ILaunchSearchFilterViewModel extends SearchFilterAdapterBase.IOnFilterItemClickListener {
    MutableLiveData<ISearchFilter> getListRocketNames();

    MutableLiveData<ISearchFilter> getListLaunchYears();

    MutableLiveData<Boolean> getCanChoice();

    MutableLiveData<Boolean> getCloseDialog();

    void onOkButtonClick();

    void initLists();

    void setLaunchSearchFilterForEdit(SearchFilterItem launchSearchFilterForEdit);

}
