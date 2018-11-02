package com.example.data.repository;

import android.graphics.Bitmap;

import com.example.data.api.SpaceXAPI;
import com.example.data.api.converter.DataToDomainConverter;
import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.ILaunchRepository;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

public class LaunchRemoteRepository implements ILaunchRepository {

    private SpaceXAPI mApi;

    public LaunchRemoteRepository(SpaceXAPI mApi) {
        this.mApi = mApi;
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return mApi.getAllPastLaunches().map(DataToDomainConverter::convertLaunchList);
    }

    @Override
    public Single<Boolean> insertLaunches(List<DomainLaunch> launches) {
        //do nothing
        return null;
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
    public Long insertLaunch(DomainLaunch domainLaunch) {
        //do nothing
        return null;
    }

    @Override
    public byte[] loadImage(String url) {
        try {
            if (url != null && !url.isEmpty()) {
                Bitmap bitmap = Picasso.get().load(url).get();
                return DbBitmapUtility.getBytes(bitmap);
            }
        } catch (Throwable throwable) {
            Timber.d(throwable);
        }
        return null;
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
        return true;
    }
}
