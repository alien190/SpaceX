package com.example.domain.repository;

import com.example.domain.model.launch.DomainLaunch;

import java.util.List;

import io.reactivex.Single;

public interface LaunchRepository {
    String REMOTE = "REMOTE";
    String LOCAL = "LOCAL";

    Single<List<DomainLaunch>> getLaunches();

    void insertLaunches(List<DomainLaunch> domainLaunches);
}
