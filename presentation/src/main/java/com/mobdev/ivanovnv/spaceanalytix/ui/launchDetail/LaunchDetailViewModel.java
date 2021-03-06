package com.mobdev.ivanovnv.spaceanalytix.ui.launchDetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.graphics.Bitmap;

import com.mobdev.data.utils.DbBitmapUtility;
import com.mobdev.domain.model.launch.DomainLaunch;
import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchDetailViewModel extends ViewModel {

    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;
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
    private MutableLiveData<Boolean> mCanPhotosStubShow = new MutableLiveData<>();
    private MutableLiveData<List<String>> mPhotosUrls = new MutableLiveData<>();
    private MutableLiveData<List<Bitmap>> mPhotos = new MutableLiveData<>();
    private MutableLiveData<Boolean> mIsRefreshPhotos = new MutableLiveData<>();


    public LaunchDetailViewModel(ILaunchService launchService,
                                 ICurrentPreferences currentPreferences,
                                 Integer flightNumber) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mFlightNumber = flightNumber;
        mIsLoadDone.setValue(true);
        mCanPhotosShow.setValue(false);
        mCanPhotosStubShow.setValue(false);
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
        if (mCurrentPreferences.isLoadBigPictures()) {
            mCanPhotosStubShow.postValue(false);
            if (checkListValue(urls)) {
                mCanPhotosShow.postValue(true);
                mDisposable.add(mLaunchService.loadImagesWithResize(urls)
                        .observeOn(Schedulers.io())
                        .doOnSubscribe(subscription -> mIsRefreshPhotos.postValue(true))
                        .subscribe(this::setPhoto, throwable -> {
                            mIsRefreshPhotos.postValue(false);
                            Timber.d(throwable);
                        }, () -> mIsRefreshPhotos.postValue(false)));
            }
        } else {
            mCanPhotosShow.setValue(false);
            mCanPhotosStubShow.setValue(true);
        }
    }

    private void setPhoto(byte[] bytes) {
        List<Bitmap> bitmapList = mPhotos.getValue();
        if (bitmapList == null) {
            bitmapList = new ArrayList<>();
        }
        bitmapList.add(DbBitmapUtility.getImage(bytes));
        mPhotos.postValue(bitmapList);
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
            String date = domainLaunch.getLaunch_date_utc();
            mMissionDate.postValue(mCurrentPreferences.getConverter().getTimeText(date));
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
        clearDisposable();
        super.onCleared();
    }

    public void clearDisposable() {
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable.clear();
        }
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

    public MutableLiveData<Boolean> getCanPhotosStubShow() {
        return mCanPhotosStubShow;
    }
}
