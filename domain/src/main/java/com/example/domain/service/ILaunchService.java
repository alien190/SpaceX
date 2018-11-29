package com.example.domain.service;

import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.filter.ISearchFilter;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ILaunchService {
    Single<List<DomainLaunch>> getLaunches();

    Flowable<List<DomainLaunch>> getLaunchesLive();

    Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(ISearchFilter searchFilter);

    void insertLaunches(List<DomainLaunch> domainLaunches);

    Maybe<Boolean> refreshLaunches();

    Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber);

    Single<DomainLaunch> getLaunchByFlightNumberWithPressKit(int flightNumber);

    Single<byte[]> loadImage(String url);

    Flowable<byte[]> loadImagesWithResize(List<String> urls);

    ISearchFilter getSearchFilter();

    IAnalyticsFilter getAnalyticsFilter();

    Flowable<DomainAnalytics> getAnalyticsLive();
}
