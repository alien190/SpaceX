package com.ivanovnv.domain.repository;

import com.ivanovnv.domain.model.analytics.DomainAnalytics;
import com.ivanovnv.domain.model.filter.IAnalyticsFilter;
import com.ivanovnv.domain.model.launch.DomainLaunch;
import com.ivanovnv.domain.model.filter.ISearchFilter;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface ILaunchRepository {
    String REMOTE = "REMOTE";
    String LOCAL = "LOCAL";

    Single<List<DomainLaunch>> getLaunches();

    Flowable<List<DomainLaunch>> getLaunchesLive();

    Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(ISearchFilter searchFilter);

    Single<Boolean> insertLaunches(List<DomainLaunch> domainLaunches);

    Long insertLaunch(DomainLaunch domainLaunch);

    Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber);

    byte[] loadImage(String url) throws Exception;

    byte[] loadImageWithResize(String url, int width, int height) throws Exception;

    int insertImage(byte[] bytes);

    int getImageId(DomainLaunch domainLaunch);

    Boolean deleteUnusedImages();

    Single<DomainLaunch> getPressKitPdf(DomainLaunch domainLaunch);

    Flowable<ISearchFilter> getSearchFilterLive();

    Single<IAnalyticsFilter> getAnalyticsFilter();

    void cancelLoadImages();

    Flowable<DomainAnalytics> getAnalyticsLive(ISearchFilter searchFilter, IAnalyticsFilter analyticsFilter);

}
