package com.example.data.repository;

import com.example.data.api.converter.DataToDomainConverter;
import com.example.data.api.converter.DomainToDataConverter;
import com.example.data.database.LaunchDao;
import com.example.data.model.DataLaunch;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.LaunchRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LaunchLocalRepository implements LaunchRepository {

    LaunchDao mLaunchDao;

    public LaunchLocalRepository(LaunchDao mLaunchDao) {
        this.mLaunchDao = mLaunchDao;
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return Single.fromCallable(() -> DataToDomainConverter.converLaunch(mLaunchDao.getLaunches()));
    }

    @Override
    public void insertLaunches(List<DomainLaunch> launches) {
        mLaunchDao.insertLaunches(DomainToDataConverter.converLaunch(launches));
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return mLaunchDao.getLaunchesLive().map(DataToDomainConverter::converLaunch);
    }
}
