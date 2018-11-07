package com.example.ivanovnv.spacex.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.domain.model.launch.DomainLaunch;

public class LaunchItemViewModel extends ViewModel {

    private MutableLiveData<String> mFlightNumber = new MutableLiveData<>();
    private MutableLiveData<String> mImageUrl = new MutableLiveData<>();
    private MutableLiveData<String> mMissionName = new MutableLiveData<>();
    private MutableLiveData<String> mRocketName = new MutableLiveData<>();
    private MutableLiveData<String> mLaunchDate = new MutableLiveData<>();
    private LaunchAdapter.OnItemClickListener mOnItemClickListener = null;

    public LaunchItemViewModel(DomainLaunch launch, LaunchAdapter.OnItemClickListener clickListener) {
        mFlightNumber.setValue(String.valueOf(launch.getFlight_number()));
        mMissionName.setValue(launch.getMission_name());
        mRocketName.setValue(launch.getRocket_name());
        mLaunchDate.setValue(launch.getLaunch_date_utc());
        mImageUrl.setValue(launch.getMission_patch_small());
        mOnItemClickListener = clickListener;
    }

    public MutableLiveData<String> getFlightNumber() {
        return mFlightNumber;
    }

    public MutableLiveData<String> getImageUrl() {
        return mImageUrl;
    }

    public MutableLiveData<String> getMissionName() {
        return mMissionName;
    }

    public MutableLiveData<String> getRocketName() {
        return mRocketName;
    }

    public MutableLiveData<String> getLaunchDate() {
        return mLaunchDate;
    }

    public void onItemClick(String flightNumber) {
        if (mOnItemClickListener != null) {
        //    mOnItemClickListener.onItemClick(Integer.valueOf(flightNumber));
        }
    }
}
