package com.example.domain.service;

import com.example.domain.model.launch.DomainLaunch;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface LaunchService {
    Single<List<DomainLaunch>> getLaunches();

    Flowable<List<DomainLaunch>> getLaunchesLive();
    void insertLaunches(List<DomainLaunch> domainLaunches);
    Single<Boolean> refreshLaunches();
}
