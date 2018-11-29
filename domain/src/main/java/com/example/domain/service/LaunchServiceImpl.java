package com.example.domain.service;

import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.filter.AnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.model.filter.SearchFilter;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.ILaunchRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LaunchServiceImpl implements ILaunchService {
    private static final int CONCURRENT_THREADS_NUMBER = 10;

    private ILaunchRepository mLocalRepository;
    private ILaunchRepository mRemoteRepository;
    private ISearchFilter mSearchFilter;
    private IAnalyticsFilter mAnalyticsFilter;
    private Disposable mSearchDisposable;
    private Disposable mAnalyticsDisposable;

    public LaunchServiceImpl(ILaunchRepository mLocalRepository, ILaunchRepository mRemoteRepository) {
        this.mLocalRepository = mLocalRepository;
        this.mRemoteRepository = mRemoteRepository;

        mSearchFilter = new SearchFilter();
        mAnalyticsFilter = new AnalyticsFilter();

        mSearchDisposable = mLocalRepository.getSearchFilterLive().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(mSearchFilter::updateFilterFromRepository, Throwable::printStackTrace);

        mAnalyticsDisposable = mLocalRepository.getAnalyticsFilter().subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(mAnalyticsFilter::updateFilterFromRepository, Throwable::printStackTrace);
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return mLocalRepository.getLaunches().subscribeOn(Schedulers.io());
    }

    @Override
    public void insertLaunches(List<DomainLaunch> domainLaunches) {
        mLocalRepository.insertLaunches(domainLaunches);
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return mLocalRepository.getLaunchesLive().subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(ISearchFilter searchFilter) {
        return mLocalRepository.getLaunchesLiveWithFilter(searchFilter).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber) {
        return mLocalRepository.getLaunchByFlightNumber(flightNumber).subscribeOn(Schedulers.io());
    }

    @Override
    public Maybe<Boolean> refreshLaunches() {
        return mRemoteRepository.getLaunches()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .parallel(CONCURRENT_THREADS_NUMBER)
                .runOn(Schedulers.io())
                .flatMap(this::updateImageIdFromDb)
                .flatMap(this::loadMissionImage)
                .map(mLocalRepository::insertLaunch)
                .sequentialDelayError()
                .lastElement()
                .map(launch -> mLocalRepository.deleteUnusedImages());
    }

    private Flowable<DomainLaunch> updateImageIdFromDb(DomainLaunch domainLaunch) {
        return Flowable.fromCallable(() -> {
            int imageId = mLocalRepository.getImageId(domainLaunch);
            domainLaunch.setImageId(imageId);
            return domainLaunch;
        });
    }

    private Flowable<DomainLaunch> loadMissionImage(DomainLaunch domainLaunch) {
        return Flowable.fromCallable(() ->
        {
            if (domainLaunch.getImageId() == 0) {
                byte[] bytes = mRemoteRepository.loadImage(domainLaunch.getMission_patch_small());
                if (bytes != null) {
                    domainLaunch.setImageId(mLocalRepository.insertImage(bytes));
                } else {
                    domainLaunch.setImageId(0);
                }
            }
            return domainLaunch;
        });
    }

    @Override
    public Single<DomainLaunch> getLaunchByFlightNumberWithPressKit(int flightNumber) {
        return getLaunchByFlightNumber(flightNumber)
                .flatMap(mRemoteRepository::getPressKitPdf)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Single<byte[]> loadImage(String url) {
        return Single.fromCallable(() -> mRemoteRepository.loadImage(url)).subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<byte[]> loadImagesWithResize(List<String> urls) {
        return Flowable.just(urls)
                .subscribeOn(Schedulers.io())
                .flatMap(Flowable::fromIterable)
                .parallel(CONCURRENT_THREADS_NUMBER)
                .runOn(Schedulers.io())
                .map(url -> mRemoteRepository.loadImageWithResize(url, 1200, 0))
                .sequentialDelayError()
                .doOnCancel(() -> mRemoteRepository.cancelLoadImages());
    }

    @Override
    public ISearchFilter getSearchFilter() {
        return mSearchFilter;
    }

    @Override
    public IAnalyticsFilter getAnalyticsFilter() {
        return mAnalyticsFilter;
    }

    @Override
    public Flowable<DomainAnalytics> getAnalyticsLive() {
        return mSearchFilter.getUpdatesLive().map(v -> true)
                .mergeWith(mAnalyticsFilter.getUpdatesLive().map(v -> true))
                .mergeWith(Flowable.just(true))
                .flatMap(v -> mLocalRepository.getAnalyticsLive(mSearchFilter, mAnalyticsFilter))
                .subscribeOn(Schedulers.io());
    }
}
