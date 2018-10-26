package com.example.data.repository;

import com.example.data.api.SpaceXAPI;
import com.example.data.api.converter.DataToDomainConverter;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.ILaunchRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class LaunchRemoteRepository implements ILaunchRepository {

    SpaceXAPI mApi;

    public LaunchRemoteRepository(SpaceXAPI mApi) {
        this.mApi = mApi;
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return mApi.getAllPastLaunches().map(DataToDomainConverter::convertLaunchList);
    }

    @Override
    public void insertLaunches(List<DomainLaunch> launches) {
        //do nothing
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return null;
    }

    @Override
    public Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber) {
        //do nothing
        return null;
    }
}
