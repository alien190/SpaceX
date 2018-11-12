package com.example.ivanovnv.spacex.launchSearchFilter;

import android.arch.lifecycle.MutableLiveData;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.launchSearch.SearchFilterAdapter;

import java.util.List;

public interface ILaunchSearchFilterViewModel extends SearchFilterAdapter.IOnFilterItemClickListener {
    MutableLiveData<List<LaunchSearchFilter>> getListRocketNames();

    MutableLiveData<List<LaunchSearchFilter>> getListLaunchYears();

    MutableLiveData<Boolean> getCanChoice();

    MutableLiveData<Boolean> getCloseDialog();

    void onOkButtonClick();

    void initLists();

    void setLaunchSearchFilterForEdit(LaunchSearchFilter launchSearchFilterForEdit);

}
