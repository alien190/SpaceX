package com.example.domain.repository;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.launch.DomainLaunchCache;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ILaunchRepository {
    String REMOTE = "REMOTE";
    String LOCAL = "LOCAL";

    Single<List<DomainLaunch>> getLaunches();

    Flowable<List<DomainLaunch>> getLaunchesLive();

    Single<Boolean> insertLaunches(List<DomainLaunch> domainLaunches);

    Long insertLaunch(DomainLaunch domainLaunch);

    Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber);

    Single<List<DomainLaunchCache>> getLaunchesCache();

    Single<Boolean> insertLaunchesCache(List<DomainLaunchCache> domainLaunches);

    //Single<List<DomainLaunch>> getLaunchFromCacheForUpdate();

    byte[] loadImage(String url);

    int insertImage(byte[] bytes);

    int getImageId(DomainLaunch domainLaunch);
}
