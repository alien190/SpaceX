package com.example.domain.repository;

import com.example.domain.model.launch.DomainLaunch;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LaunchRepository {
    String REMOTE = "REMOTE";
    String LOCAL = "LOCAL";

    Single<List<DomainLaunch>> getLaunches();

    Flowable<List<DomainLaunch>> getLaunchesLive();
    void insertLaunches(List<DomainLaunch> domainLaunches);

}
