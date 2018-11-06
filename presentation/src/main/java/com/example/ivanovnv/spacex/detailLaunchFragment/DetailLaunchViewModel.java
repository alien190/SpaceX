package com.example.ivanovnv.spacex.detailLaunchFragment;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.service.ILaunchService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class DetailLaunchViewModel extends ViewModel {

    private ILaunchService mLaunchService;
    private Integer mFlightNumber;
    private Disposable mDisposable;
    private DomainLaunch mDomainLaunch;
    private MutableLiveData<Bitmap> mMissionImage = new MutableLiveData<>();
    private MutableLiveData<String> mMissionName = new MutableLiveData<>();
    private MutableLiveData<String> mMissionDetails = new MutableLiveData<>();
    private MutableLiveData<String> mMissionDate = new MutableLiveData<>();


    public DetailLaunchViewModel(ILaunchService launchService, Integer flightNumber) {
        mLaunchService = launchService;
        mFlightNumber = flightNumber;
    }

    public void loadLaunch() {
        if (mFlightNumber != null) {
            mDisposable = mLaunchService.getLaunchByFlightNumber(mFlightNumber)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setLaunch, Timber::d);
        }
    }

    private void setLaunch(DomainLaunch domainLaunch) {
        mDomainLaunch = domainLaunch;
        if (domainLaunch != null) {
            mMissionImage.postValue(DbBitmapUtility.getImage(domainLaunch.getImage()));
            mMissionName.setValue(domainLaunch.getMission_name());
            mMissionDetails.setValue(domainLaunch.getDetails());
            mMissionDate.setValue(domainLaunch.getLaunch_date_utc());
        }
    }

    @Override
    protected void onCleared() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onCleared();
    }

    public MutableLiveData<Bitmap> getMissionImage() {
        return mMissionImage;
    }

    public MutableLiveData<String> getMissionName() {
        return mMissionName;
    }

    public MutableLiveData<String> getMissionDetails() {
        return mMissionDetails;
    }

    public MutableLiveData<String> getMissionDate() {
        return mMissionDate;
    }

    public Integer getFlightNumber() {
        return mFlightNumber;
    }
}
