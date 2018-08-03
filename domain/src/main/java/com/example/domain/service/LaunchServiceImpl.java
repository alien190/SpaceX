package com.example.domain.service;

import com.example.domain.model.launch.Launch;

import java.util.List;

import io.reactivex.Single;

public class LaunchServiceImpl implements LaunchService {
    @Override
    public Single<List<Launch>> getLaunches() {
        return null;
    }

    @Override
    public void insertLaunches(List<Launch> launches) {

    }
}
