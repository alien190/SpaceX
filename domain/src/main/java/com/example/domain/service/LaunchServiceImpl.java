package com.example.domain.service;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.domain.model.searchFilter.LaunchSearchType;
import com.example.domain.repository.ILaunchRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LaunchServiceImpl implements ILaunchService {

    private ILaunchRepository mLocalRepository;
    private ILaunchRepository mRemoteRepository;
    private static final int CONCURRENT_THREADS_NUMBER = 10;

    public LaunchServiceImpl(ILaunchRepository mLocalRepository, ILaunchRepository mRemoteRepository) {
        this.mLocalRepository = mLocalRepository;
        this.mRemoteRepository = mRemoteRepository;
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
    public Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(List<LaunchSearchFilter> launchSearchFilterList) {
        return mLocalRepository.getLaunchesLiveWithFilter(launchSearchFilterList).subscribeOn(Schedulers.io());
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
    public Single<List<LaunchSearchFilter>> getRocketNamesFilterList() {
        return mLocalRepository.getListRocketNames()
                .subscribeOn(Schedulers.io())
                .map(this::createLaunchSearchFilterListRocketNames);
    }

    @Override
    public Single<List<LaunchSearchFilter>> getLaunchYearsFilterList() {
        return mLocalRepository.getListLaunchYears()
                .subscribeOn(Schedulers.io())
                .map(this::createLaunchSearchFilterListLaunchYears);
    }

    public List<LaunchSearchFilter> createLaunchSearchFilterListRocketNames(List<String> stringList) throws Exception {
        return createLaunchSearchFilterList(stringList, LaunchSearchType.BY_ROCKET_NAME);
    }

    public List<LaunchSearchFilter> createLaunchSearchFilterListLaunchYears(List<String> stringList) throws Exception {
        return createLaunchSearchFilterList(stringList, LaunchSearchType.BY_LAUNCH_YEAR);
    }


    public List<LaunchSearchFilter> createLaunchSearchFilterList(List<String> stringList, LaunchSearchType type) throws Exception {
        List<LaunchSearchFilter> launchSearchFilterList = new ArrayList<>();
        for (String value : stringList) {
            launchSearchFilterList.add(new LaunchSearchFilter(value, type));
        }
        return launchSearchFilterList;
    }
}
