package com.example.data.repository;

import android.graphics.Bitmap;
import android.text.BoringLayout;

import com.example.data.api.SpaceXAPI;
import com.example.data.api.converter.DataToDomainConverter;
import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.launch.DomainLaunchCache;
import com.example.domain.repository.ILaunchRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

public class LaunchRemoteRepository implements ILaunchRepository {

    SpaceXAPI mApi;

    public LaunchRemoteRepository(SpaceXAPI mApi) {
        this.mApi = mApi;
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return mApi.getAllPastLaunches().map(DataToDomainConverter::convertLaunchList);
    }

    @Override
    public void insertLaunches(List<DomainLaunch> launches) {
        //do nothing
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return null;
    }

    @Override
    public Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber) {
        return mApi.getLaunchByFlightNumber(flightNumber).map(DataToDomainConverter::convertLaunch);
    }

    @Override
    public Single<List<DomainLaunchCache>> getLaunchesCache() {
        return mApi.getAllPastLaunchesCache().map(DataToDomainConverter::convertLaunchCacheList);
    }

    @Override
    public Single<Boolean> insertLaunchesCache(List<DomainLaunchCache> domainLaunches) {
        // do nothing
        return null;
    }

    @Override
    public Single<List<DomainLaunchCache>> getLaunchCacheForLoadImage() {
        //do nothing
        return null;
    }

    @Override
    public Long insertLaunch(DomainLaunch domainLaunch) {
        //do nothing
        return null;
    }

    @Override
    public DomainLaunch loadImage(DomainLaunch launch) {
        try {
            String url = launch.getMission_patch_small();
            if (url != null && !url.isEmpty()) {
                Bitmap bitmap = Picasso.get().load(url).get();
                byte[] bytes = DbBitmapUtility.getBytes(bitmap);
                launch.setImage(bytes);
            }
        } catch (Throwable throwable) {
            Timber.d(throwable);
        }
        return launch;
    }
}
