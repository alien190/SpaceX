package com.mobdev.data.repository;

import android.graphics.Bitmap;

import com.mobdev.data.api.SpaceXAPI;
import com.mobdev.data.utils.DataToDomainConverter;
import com.mobdev.data.utils.DbBitmapUtility;
import com.mobdev.domain.model.analytics.DomainAnalytics;
import com.mobdev.domain.model.filter.IAnalyticsFilter;
import com.mobdev.domain.model.launch.DomainLaunch;
import com.mobdev.domain.model.filter.ISearchFilter;
import com.mobdev.domain.repository.ILaunchRepository;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import timber.log.Timber;

public class LaunchRemoteRepository implements ILaunchRepository {

    private SpaceXAPI mApi;
    private OkHttpClient mOkHttpClient;

    public LaunchRemoteRepository(SpaceXAPI mApi, OkHttpClient okHttpClient) {
        this.mApi = mApi;
        this.mOkHttpClient = okHttpClient;
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return mApi.getAllPastLaunches().map(DataToDomainConverter::convertLaunchList);
    }

    @Override
    public Single<Boolean> insertLaunches(List<DomainLaunch> launches) {
        return Single.error(getError());
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return Flowable.error(getError());
    }

    @Override
    public Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber) {
        return mApi.getLaunchByFlightNumber(flightNumber).map(DataToDomainConverter::convertLaunch);
    }


    @Override
    public Long insertLaunch(DomainLaunch domainLaunch) {
        //do nothing
        return null;
    }

    @Override
    public byte[] loadImage(String url) throws Exception {
        if (url != null && !url.isEmpty()) {
            Bitmap bitmap = Picasso.get().load(url).get();
            return DbBitmapUtility.getBytes(bitmap);
        } else {
            throw new IllegalArgumentException("URL is null or empty");
        }
    }

    @Override
    public byte[] loadImageWithResize(String url, int width, int height) throws Exception {
        if (url != null && !url.isEmpty()) {
            Bitmap bitmap = Picasso.get()
                    .load(url)
                    .tag("loadImageWithResize")
                    .resize(width, height)
                    .get();
            return DbBitmapUtility.getBytes(bitmap);
        } else {
            throw new IllegalArgumentException("URL is null or empty");
        }
    }

    @Override
    public int insertImage(byte[] bytes) {
        return 0;
    }

    @Override
    public int getImageId(DomainLaunch domainLaunch) {
        return 0;
    }

    @Override
    public Boolean deleteUnusedImages() {
        //do nothing
        return false;
    }

    public Single<DomainLaunch> getPressKitPdf(DomainLaunch domainLaunch) {
        return Single.fromCallable(() -> {
            InputStream inputStream = loadPdf(domainLaunch.getPresskit());
            // domainLaunch.setPresskitStream(inputStream);
            return domainLaunch;
        }).subscribeOn(Schedulers.io());

    }

    private InputStream loadPdf(String url) throws IOException {
        Request request = new Request.Builder()
                .cacheControl(new CacheControl.Builder()
                        .maxStale(30, TimeUnit.DAYS)
                        .build())
                .url(url)
                .build();
        return mOkHttpClient.newCall(request).execute().body().byteStream();
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(ISearchFilter searchFilter) {
        return Flowable.error(getError());
    }

    @Override
    public void cancelLoadImages() {
        Timber.d("cancelLoadImages");
        Picasso.get().cancelTag("loadImageWithResize");
    }

    @Override
    public Flowable<ISearchFilter> getSearchFilterLive() {
        return Flowable.error(getError());
    }

    @Override
    public Single<IAnalyticsFilter> getAnalyticsFilter() {
        return Single.error(getError());
    }

    @Override
    public Flowable<DomainAnalytics> getAnalyticsLive(ISearchFilter searchFilter, IAnalyticsFilter analyticsFilter) {
        return Flowable.error(getError());
    }

    private Throwable getError() {
        return new Throwable("do nothing");
    }
}
