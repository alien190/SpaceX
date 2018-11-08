package com.example.domain.repository;

import com.example.domain.model.launch.DomainLaunch;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ILaunchRepository {
    String REMOTE = "REMOTE";
    String LOCAL = "LOCAL";

    Single<List<DomainLaunch>> getLaunches();

    Flowable<List<DomainLaunch>> getLaunchesLive();

    Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(String filter);

    Single<Boolean> insertLaunches(List<DomainLaunch> domainLaunches);

    Long insertLaunch(DomainLaunch domainLaunch);

    Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber);

    //Single<List<DomainLaunch>> getLaunchFromCacheForUpdate();

    byte[] loadImage(String url) throws Exception;

    byte[] loadImageWithResize(String url, int width, int height) throws Exception;

    int insertImage(byte[] bytes);

    int getImageId(DomainLaunch domainLaunch);

    Boolean deleteUnusedImages();

    Single<DomainLaunch> getPressKitPdf(DomainLaunch domainLaunch);
}
