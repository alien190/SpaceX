package com.example.data.repository;

import com.example.data.api.converter.DataToDomainConverter;
import com.example.data.api.converter.DomainToDataConverter;
import com.example.data.database.LaunchDao;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.LaunchRepository;

import java.util.List;

import io.reactivex.Single;

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

}
