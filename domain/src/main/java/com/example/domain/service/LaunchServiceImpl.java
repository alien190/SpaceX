package com.example.domain.service;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.ILaunchRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class LaunchServiceImpl implements ILaunchService {

    private ILaunchRepository mLocalRepository;
    private ILaunchRepository mRemoteRepository;

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

//    @Override
//    public Single<Boolean> refreshLaunches() {
//        return mRemoteRepository.getLaunches()
//                .subscribeOn(Schedulers.io())
//                .doOnSuccess(this::insertLaunches)
//                .map(domainLaunches -> true);
//
//    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return mLocalRepository.getLaunchesLive().subscribeOn(Schedulers.io());
    }

    @Override
    public Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber) {
        return mLocalRepository.getLaunchByFlightNumber(flightNumber).subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<Long> refreshLaunches() {
        return mRemoteRepository.getLaunchesCache()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(mLocalRepository::insertLaunchesCache)
                .flatMap(aBoolean -> mLocalRepository.getLaunchCacheForLoadImage())
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .parallel(10)
                .runOn(Schedulers.io())
                .flatMap(launch -> mRemoteRepository
                        .getLaunchByFlightNumber(launch.getFlight_number())
                        .toFlowable())
                .map(mRemoteRepository::loadImage)
                .flatMap(domainLaunch -> Flowable.fromCallable(
                        () -> mLocalRepository.insertLaunch(domainLaunch)))
                .sequential();

    }
}
