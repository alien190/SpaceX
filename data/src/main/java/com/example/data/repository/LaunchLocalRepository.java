package com.example.data.repository;

import android.arch.persistence.db.SimpleSQLiteQuery;

import com.example.data.model.DataFilterItem;
import com.example.data.utils.converter.DataToDomainConverter;
import com.example.data.utils.converter.DomainToDataConverter;
import com.example.data.database.LaunchDao;
import com.example.data.model.DataImage;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.ISearchFilterItem;
import com.example.domain.model.searchFilter.SearchFilter;
import com.example.domain.model.searchFilter.SearchFilterItem;
import com.example.domain.model.searchFilter.SearchFilterItemType;
import com.example.domain.repository.ILaunchRepository;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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

    @Override
    public Boolean deleteUnusedImages() {
        Timber.d("deleteUnusedImages");
        mLaunchDao.deleteUnusedImages();
        return true;
    }

    @Override
    public Single<DomainLaunch> getPressKitPdf(DomainLaunch domainLaunch) {
        return Single.error(getError());
    }

    @Override
    public Flowable<List<DomainLaunch>> getLaunchesLiveWithFilter(ISearchFilter searchFilter) {
        String filter = generateSqlWhereFromFilterList(searchFilter);
        if (!filter.isEmpty()) {
            filter = " AND (" + filter + ") ";
        } else {
            filter = " ";
        }

        String query = "SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id"
                + filter
                + "ORDER BY launch_date_unix DESC";
        Timber.d("SQL query: %s", query);
        return mLaunchDao
                .getLaunchesLiveWithFilter(new SimpleSQLiteQuery(query))
                .map(DataToDomainConverter::convertLaunchList);
    }

    private String generateSqlWhereFromFilterList(ISearchFilter searchFilter) {
        List<ISearchFilterItem> launchSearchFilterList = searchFilter.getItems();
        if (launchSearchFilterList == null || launchSearchFilterList.isEmpty()) {
            return "";
        } else {
            StringBuilder retValueBuilder = new StringBuilder();
            String filter = "";
            for (ISearchFilterItem launchSearchFilter : launchSearchFilterList) {
                switch (launchSearchFilter.getType()) {
                    case BY_MISSION_NAME: {
                        filter = "mission_name LIKE '%" + launchSearchFilter.getValue() + "%' ";
                        break;
                    }
                    case BY_ROCKET_NAME: {
                        filter = "rocket_name LIKE '%" + launchSearchFilter.getValue() + "%' ";
                        break;
                    }
                    case BY_LAUNCH_YEAR: {
                        filter = "launch_year LIKE '%" + launchSearchFilter.getValue() + "%' ";
                        break;
                    }
                }
                if (!retValueBuilder.toString().isEmpty()) {
                    retValueBuilder.append(" OR ");
                }
                retValueBuilder.append(filter);
            }
            return retValueBuilder.toString();
        }
    }


//    @Override
//    public Single<List<String>> getListRocketNames() {
//        return mLaunchDao.getListRocketNames();
//    }
//
//    @Override
//    public Single<List<String>> getListLaunchYears() {
//        return mLaunchDao.getListLaunchYears();
//    }


    @Override
    public Flowable<ISearchFilter> getSearchFilterLive() {
        return mLaunchDao.getFilterListItems().map(this::getSearchFilter);
    }

    private ISearchFilter getSearchFilter(List<DataFilterItem> filterItems) {
        ISearchFilter searchFilter = new SearchFilter();
        for (DataFilterItem item : filterItems) {
            switch (item.getType()) {
                case 1: {
                    searchFilter.addItem(item.getValue(), SearchFilterItemType.BY_ROCKET_NAME);
                    break;
                }
                case 2: {
                    searchFilter.addItem(item.getValue(), SearchFilterItemType.BY_LAUNCH_YEAR);
                    break;
                }
            }

        }
        return searchFilter;
    }

    @Override
    public void cancelLoadImages() {
        //do nothing
    }

    private Throwable getError() {
        return new Throwable("do nothing");
    }
}
