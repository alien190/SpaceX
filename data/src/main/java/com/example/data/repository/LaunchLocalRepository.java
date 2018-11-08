package com.example.data.repository;

import android.arch.persistence.db.SimpleSQLiteQuery;

import com.example.data.api.converter.DataToDomainConverter;
import com.example.data.api.converter.DomainToDataConverter;
import com.example.data.database.LaunchDao;
import com.example.data.database.LaunchDataBase;
import com.example.data.model.DataImage;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.repository.ILaunchRepository;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

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
    public Long insertLaunch(DomainLaunch domainLaunch) {
        return mLaunchDao.insertLaunch(DomainToDataConverter.convertLaunch(domainLaunch));
    }

    @Override
    public byte[] loadImage(String url) {
        return null;
    }

    @Override
    public byte[] loadImageWithResize(String url, int width, int height) throws Exception {
        return new byte[0];
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

    @Override
    public Boolean deleteUnusedImages() {
        Timber.d("deleteUnusedImages");
        mLaunchDao.deleteUnusedImages();
        return true;
    }

    @Override
    public Single<DomainLaunch> getPressKitPdf(DomainLaunch domainLaunch) {
        //do nothing
        return null;
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(String filter) {
        String filterQuery = "";
        if (filter != null && !filter.isEmpty()) {
            filterQuery = "AND mission_name LIKE '%" + filter + "%'";
        }
        String query = "SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id "
                + filterQuery
                + " ORDER BY launch_date_unix DESC";
        return mLaunchDao
                .getLaunchesLiveWithFilter(new SimpleSQLiteQuery(query))
                .map(DataToDomainConverter::convertLaunchList);
    }
}
