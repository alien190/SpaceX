package com.example.domain.service;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.LaunchRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LaunchServiceImpl implements LaunchService {

    private LaunchRepository mLocalRepository;
    private LaunchRepository mRemoteRepository;

    public LaunchServiceImpl(LaunchRepository mLocalRepository, LaunchRepository mRemoteRepository) {
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
    public Single<Boolean> refreshLaunches() {
        return mRemoteRepository.getLaunches()
                .subscribeOn(Schedulers.io())
                .doOnSuccess(this::insertLaunches)
                .map(domainLaunches -> true);

    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return mLocalRepository.getLaunchesLive().subscribeOn(Schedulers.io());
    }
}
