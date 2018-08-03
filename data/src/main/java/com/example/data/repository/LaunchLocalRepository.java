package com.example.data.repository;

import com.example.data.database.LaunchDao;
import com.example.domain.model.launch.Launch;
import com.example.domain.repository.LaunchRepository;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

public class LaunchLocalRepository implements LaunchRepository {

    LaunchDao mLaunchDao;

    public LaunchLocalRepository(LaunchDao mLaunchDao) {
        this.mLaunchDao = mLaunchDao;
    }

    @Override
    public Single<List<Launch>> getLaunches() {
        return Single.fromCallable(new Callable<List<Launch>>() {
            @Override
            public List<Launch> call() throws Exception {
                return mLaunchDao.getLaunches();
            }
        })
    }

    @Override
    public void insertLaunches(List<Launch> launches) {

    }

    private List<com>
}
