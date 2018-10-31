package com.example.domain.service;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.launch.DomainLaunchCache;
import com.example.domain.repository.ILaunchRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
    public Observable<Long> refreshLaunches() {
        return mRemoteRepository.getLaunchesCash()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(mLocalRepository::insertLaunchesCash)
                .flatMap(aBoolean -> mLocalRepository.getLaunchCacheForLoadImage())
                .toObservable()
                .flatMap(Observable::fromIterable)
                .flatMap(launch -> mRemoteRepository
                        .getLaunchByFlightNumber(launch.getFlight_number())
                        .toObservable())
                .map(mRemoteRepository::loadImage)
                .flatMap(domainLaunch -> Observable.fromCallable(
                        () -> mLocalRepository.insertLaunch(domainLaunch)))
                .subscribeOn(Schedulers.io());
    }
}
