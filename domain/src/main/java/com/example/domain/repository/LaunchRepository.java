package com.example.domain.repository;

import com.example.domain.model.launch.Launch;

import java.util.List;

import io.reactivex.Single;

public interface LaunchRepository {
    String REMOTE = "REMOTE";
    String LOCAL = "LOCAL";

    Single<List<Launch>> getLaunches();

    void insertLaunches(List<Launch> launches);
}
