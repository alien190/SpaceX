package com.example.ivanovnv.spacex.detailLaunch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.service.ILaunchService;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class DetailLaunchViewModel extends ViewModel {

    private ILaunchService mLaunchService;
    private Integer mFlightNumber;
    private CompositeDisposable mDisposable = new CompositeDisposable();
    private DomainLaunch mDomainLaunch = null;
    private MutableLiveData<Bitmap> mMissionImage = new MutableLiveData<>();
    private MutableLiveData<String> mMissionName = new MutableLiveData<>();
    private MutableLiveData<String> mMissionDetails = new MutableLiveData<>();
    private MutableLiveData<String> mMissionDate = new MutableLiveData<>();
    private MutableLiveData<String> mArticleLink = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanArticleShow = new MutableLiveData<>();
    private MutableLiveData<String> mWikipediaLink = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanWikipediaShow = new MutableLiveData<>();
    private MutableLiveData<String> mYouTubeLink = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanYouTubeShow = new MutableLiveData<>();
    private MutableLiveData<String> mPressReleaseLink = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanPressReleaseShow = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanLinksSectionShow = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanMissionDetailsShow = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsLoadDone = new MutableLiveData<>();
    private MutableLiveData<String> mExternalLinkForOpen = new MutableLiveData<>();
    private MutableLiveData<Boolean> mCanPhotosShow = new MutableLiveData<>();
    private MutableLiveData<List<String>> mPhotosUrls = new MutableLiveData<>();
    private MutableLiveData<List<Bitmap>> mPhotos = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsRefreshPhotos = new MutableLiveData<>();


    public DetailLaunchViewModel(ILaunchService launchService, Integer flightNumber) {
        mLaunchService = launchService;
        mFlightNumber = flightNumber;
        mIsLoadDone.postValue(true);
        initVisibilitySubscribers();
    }

    private void initVisibilitySubscribers() {
        mArticleLink.observeForever(v -> mCanArticleShow.postValue(checkLinkValue(v)));
        mYouTubeLink.observeForever(v -> mCanYouTubeShow.postValue(checkLinkValue(v)));
        mWikipediaLink.observeForever(v -> mCanWikipediaShow.postValue(checkLinkValue(v)));
        mPressReleaseLink.observeForever(v -> mCanPressReleaseShow.postValue(checkLinkValue(v)));
        mMissionDetails.observeForever(v -> mCanMissionDetailsShow.postValue(checkLinkValue(v)));
        mPhotosUrls.observeForever(this::loadPhotos);
        mCanArticleShow.observeForever(this::checkLinksSectionShow);
        mCanYouTubeShow.observeForever(this::checkLinksSectionShow);
        mCanWikipediaShow.observeForever(this::checkLinksSectionShow);
        mCanPressReleaseShow.observeForever(this::checkLinksSectionShow);
    }

    private void loadPhotos(List<String> urls) {
        if (checkListValue(urls)) {
            mDisposable.add(mLaunchService.loadImages(urls)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(subscription -> mIsRefreshPhotos.postValue(true))
                    .subscribe(this::setPhoto, throwable -> {
                        mIsRefreshPhotos.postValue(false);
                        Timber.d(throwable);
                    }, () -> mIsRefreshPhotos.postValue(false)));
        }
    }

    private void setPhoto(byte[] bytes) {
        List<Bitmap> bitmapList = mPhotos.getValue();
        if (bitmapList == null) {
            bitmapList = new ArrayList<>();
        }
        bitmapList.add(DbBitmapUtility.getImage(bytes));
        mPhotos.postValue(bitmapList);
        mCanPhotosShow.postValue(true);
    }

    private Boolean checkLinkValue(String value) {
        return value != null && !value.isEmpty();
    }

    private Boolean checkListValue(List<String> list) {
        return list != null && !list.isEmpty();
    }

    private void checkLinksSectionShow(Boolean b) {
        mCanLinksSectionShow.postValue(
                checkBooleanValue(mCanPressReleaseShow)
                        || checkBooleanValue(mCanYouTubeShow)
                        || checkBooleanValue(mCanArticleShow)
                        || checkBooleanValue(mCanWikipediaShow)
        );
    }

    private Boolean checkBooleanValue(MutableLiveData<Boolean> value) {
        return value != null && value.getValue() != null && value.getValue();
    }

    public void loadLaunch() {
        if (mDomainLaunch == null) {
            loadLaunchForced();
        }
    }

    public void loadLaunchForced() {
        if (mFlightNumber != null) {
            mDisposable.add(mLaunchService.getLaunchByFlightNumber(mFlightNumber)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setLaunch, Timber::d));
        }
    }

    private void setLaunch(DomainLaunch domainLaunch) {
        mDomainLaunch = domainLaunch;
        if (domainLaunch != null) {
            mMissionImage.postValue(DbBitmapUtility.getImage(domainLaunch.getImage()));
            mMissionName.postValue(checkStringValue(domainLaunch.getMission_name()));
            mMissionDetails.postValue(checkStringValue(domainLaunch.getDetails()));
            mMissionDate.postValue(checkStringValue(domainLaunch.getLaunch_date_utc()));
            mArticleLink.postValue(checkStringValue(domainLaunch.getArticle_link()));
            mYouTubeLink.postValue(checkStringValue(domainLaunch.getVideo_link()));
            mPressReleaseLink.postValue(checkStringValue(domainLaunch.getPresskit()));
            mWikipediaLink.postValue(checkStringValue(domainLaunch.getWikipedia()));
            mPhotosUrls.postValue(new ArrayList<>(domainLaunch.getFlickr_images()));
            mIsLoadDone.postValue(true);
        }
    }

    private String checkStringValue(String value) {
        return value != null && !value.isEmpty() ? value : "";
    }

    public void openExternalLink(String link) {
        mExternalLinkForOpen.postValue(link);
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

    public MutableLiveData<String> getArticleLink() {
        return mArticleLink;
    }

    public MutableLiveData<Boolean> getCanArticleShow() {
        return mCanArticleShow;
    }

    public MutableLiveData<String> getWikipediaLink() {
        return mWikipediaLink;
    }

    public MutableLiveData<Boolean> getCanWikipediaShow() {
        return mCanWikipediaShow;
    }

    public MutableLiveData<String> getYouTubeLink() {
        return mYouTubeLink;
    }

    public MutableLiveData<Boolean> getCanYouTubeShow() {
        return mCanYouTubeShow;
    }

    public MutableLiveData<String> getPressReleaseLink() {
        return mPressReleaseLink;
    }

    public MutableLiveData<Boolean> getCanPressReleaseShow() {
        return mCanPressReleaseShow;
    }

    public MutableLiveData<Boolean> getCanLinksSectionShow() {
        return mCanLinksSectionShow;
    }

    public MutableLiveData<Boolean> getCanMissionDetailsShow() {
        return mCanMissionDetailsShow;
    }

    public MutableLiveData<Boolean> getIsLoadDone() {
        return mIsLoadDone;
    }

    public MutableLiveData<String> getExternalLinkForOpen() {
        return mExternalLinkForOpen;
    }

    public MutableLiveData<Boolean> getCanPhotosShow() {
        return mCanPhotosShow;
    }

    public MutableLiveData<List<String>> getPhotosUrls() {
        return mPhotosUrls;
    }

    public MutableLiveData<List<Bitmap>> getPhotos() {
        return mPhotos;
    }

    public MutableLiveData<Boolean> getIsRefreshPhotos() {
        return mIsRefreshPhotos;
    }
}
