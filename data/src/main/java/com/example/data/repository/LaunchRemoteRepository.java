package com.example.data.repository;

import com.example.data.api.SpaceXAPI;
import com.example.data.api.converter.DataToDomainConverter;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.LaunchRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class LaunchRemoteRepository implements LaunchRepository {

    SpaceXAPI mApi;

    public LaunchRemoteRepository(SpaceXAPI mApi) {
        this.mApi = mApi;
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return mApi.getAllPastLaunches().map(DataToDomainConverter::converLaunch);
    }

    @Override
    public void insertLaunches(List<DomainLaunch> launches) {
        //do nothing
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return null;
    }
}
