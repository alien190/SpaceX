package com.example.data.repository;

import com.example.data.api.converter.DataToDomainConverter;
import com.example.data.api.converter.DomainToDataConverter;
import com.example.data.database.LaunchDao;
import com.example.data.model.DataImage;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.launch.DomainLaunchCache;
import com.example.domain.repository.ILaunchRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class LaunchLocalRepository implements ILaunchRepository {

    LaunchDao mLaunchDao;

    public LaunchLocalRepository(LaunchDao mLaunchDao) {
        this.mLaunchDao = mLaunchDao;
    }

    @Override
    public Single<List<DomainLaunch>> getLaunches() {
        return Single.fromCallable(() -> DataToDomainConverter.convertLaunchList(mLaunchDao.getLaunches()));
    }

    @Override
    public Single<Boolean> insertLaunches(List<DomainLaunch> launches) {
        return Single.fromCallable(() -> {
            mLaunchDao.insertLaunches(DomainToDataConverter.convertLaunchList(launches));
            return true;
        });
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLive() {
        return mLaunchDao.getLaunchesLive().map(DataToDomainConverter::convertLaunchList);
    }

    @Override
    public Single<DomainLaunch> getLaunchByFlightNumber(int flightNumber) {
        return mLaunchDao.getLaunchByFlightNumber(flightNumber).map(DataToDomainConverter::convertLaunch);
    }

    @Override
    public Single<List<DomainLaunchCache>> getLaunchesCache() {
        //do noting
        return null;
    }

    @Override
    public Single<Boolean> insertLaunchesCache(List<DomainLaunchCache> domainLaunches) {
        return Single.fromCallable(() -> {
            mLaunchDao.insertLaunchesCache(DomainToDataConverter.convertLaunchCacheList(domainLaunches));
            return true;
        });
    }

//    @Override
//    public Single<List<DomainLaunch>> getLaunchFromCacheForUpdate() {
//        return mLaunchDao.getLaunchFromCacheForUpdate().map(DataToDomainConverter::convertLaunchList);
//    }

    @Override
    public Long insertLaunch(DomainLaunch domainLaunch) {
        return mLaunchDao.insertLaunch(DomainToDataConverter.convertLaunch(domainLaunch));
    }

    @Override
    public byte[] loadImage(String url) {
        return null;
    }

    @Override
    public int insertImage(byte[] bytes) {
        DataImage dataImage = new DataImage();
        dataImage.setImage(bytes);
        return (int) mLaunchDao.insertImage(dataImage);
    }

    @Override
    public int getImageId(DomainLaunch domainLaunch) {
        return mLaunchDao.getImageId(domainLaunch.getFlight_number(), domainLaunch.getMission_patch_small());
    }
}
