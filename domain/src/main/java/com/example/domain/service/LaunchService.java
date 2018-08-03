package com.example.domain.service;

import com.example.domain.model.launch.Launch;

import java.util.List;

import io.reactivex.Single;

public interface LaunchService {
    Single<List<Launch>> getLaunches();

    void insertLaunches(List<Launch> launches);
}
