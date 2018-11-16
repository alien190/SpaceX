package com.example.data.repository;

import android.arch.persistence.db.SimpleSQLiteQuery;

import com.example.data.model.DataFilterItem;
import com.example.data.utils.converter.DataToDomainConverter;
import com.example.data.utils.converter.DomainToDataConverter;
import com.example.data.database.LaunchDao;
import com.example.data.model.DataImage;
import com.example.domain.model.filter.AnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilterItem;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.model.filter.IBaseFilterItem;
import com.example.domain.model.filter.SearchFilter;
import com.example.domain.repository.ILaunchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

import static com.example.domain.model.filter.IAnalyticsFilter.ItemType.LAUNCH_COUNT;
import static com.example.domain.model.filter.IAnalyticsFilter.ItemType.PAYLOAD_WEIGHT;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.COUNTRIES;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.MISSIONS;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.ORBITS;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.YEARS;

public class LaunchLocalRepository implements ILaunchRepository {

    private LaunchDao mLaunchDao;

    private static final int BY_MISSION_NAME_INDEX = 1;
    private static final int BY_ROCKET_NAME_INDEX = 2;
    private static final int BY_LAUNCH_YEAR_INDEX = 3;

    private static final HashMap<Integer, ISearchFilter.ItemType> mItemTypeByIndex =
            new HashMap<Integer, ISearchFilter.ItemType>() {
                {
                    put(BY_MISSION_NAME_INDEX, ISearchFilter.ItemType.BY_MISSION_NAME);
                    put(BY_ROCKET_NAME_INDEX, ISearchFilter.ItemType.BY_ROCKET_NAME);
                    put(BY_LAUNCH_YEAR_INDEX, ISearchFilter.ItemType.BY_LAUNCH_YEAR);
                }
            };

    private static final HashMap<ISearchFilter.ItemType, String> mColunmNameByItemType =
            new HashMap<ISearchFilter.ItemType, String>() {
                {
                    put(ISearchFilter.ItemType.BY_MISSION_NAME, "mission_name");
                    put(ISearchFilter.ItemType.BY_ROCKET_NAME, "rocket_name");
                    put(ISearchFilter.ItemType.BY_LAUNCH_YEAR, "launch_year");
                }
            };

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
        ISearchFilter searchFilterCopy = new SearchFilter(searchFilter);

        String textQuery = searchFilterCopy.getTextQuery();
        if (textQuery != null && !textQuery.isEmpty()) {
            searchFilterCopy.addItem(textQuery, ISearchFilter.ItemType.BY_MISSION_NAME);
        }

        String filter = generateSqlWhereConditions(searchFilterCopy);
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

    String generateSqlWhereConditions(ISearchFilter searchFilter) {
        String retValue;
        StringBuilder retValueBuilder = new StringBuilder();
        String filter;
        Set<Map.Entry<Integer, ISearchFilter.ItemType>> entries = mItemTypeByIndex.entrySet();
        for (Map.Entry<Integer, ISearchFilter.ItemType> entry : entries) {
            filter = generateSqlWhereConditionsForType(searchFilter, entry.getValue());
            if (!filter.isEmpty()) {
                if (!retValueBuilder.toString().isEmpty()) {
                    retValueBuilder.append(" AND ");
                }
                retValueBuilder.append("(");
                retValueBuilder.append(filter);
                retValueBuilder.append(")");
            }
        }
        retValue = retValueBuilder.toString();
        return retValue;
    }

    private String generateSqlWhereConditionsForType(ISearchFilter searchFilter, ISearchFilter.ItemType type) {
        StringBuilder retValueBuilder = new StringBuilder();
        ISearchFilter filter = searchFilter.getFilterByType(type).getSelectedFilter();

        IBaseFilterItem item;
        int count = filter.getItemsCount();
        for (int i = 0; i < count; i++) {
            item = filter.getItem(i);
            if (item != null) {
                if (!retValueBuilder.toString().isEmpty()) {
                    retValueBuilder.append(" OR ");
                }
                retValueBuilder.append(mColunmNameByItemType.get(item.getType()));
                retValueBuilder.append(" LIKE '%");
                retValueBuilder.append(item.getValue());
                retValueBuilder.append("%'");
            }
        }
        return retValueBuilder.toString();
    }


    @Override
    public Flowable<ISearchFilter> getSearchFilterLive() {
        return mLaunchDao.getFilterListItems(BY_ROCKET_NAME_INDEX, BY_LAUNCH_YEAR_INDEX).map(this::getSearchFilter);
    }

    private ISearchFilter getSearchFilter(List<DataFilterItem> filterItems) {
        ISearchFilter searchFilter = new SearchFilter();
        for (DataFilterItem item : filterItems) {
            searchFilter.addItem(item.getValue(), mItemTypeByIndex.get(item.getType()));
        }
        return searchFilter;
    }

    @Override
    public void cancelLoadImages() {
        //do nothing
    }

    @Override
    public Single<IAnalyticsFilter> getAnalyticsFilter() {
        return Single.fromCallable(() -> {
            IAnalyticsFilter analyticsFilter = new AnalyticsFilter();
            analyticsFilter.addItem("По годам", PAYLOAD_WEIGHT, YEARS);
            analyticsFilter.addItem("По орбитам", PAYLOAD_WEIGHT, ORBITS);
            analyticsFilter.addItem("По миссиям", PAYLOAD_WEIGHT, MISSIONS);
            analyticsFilter.addItem("По странам", PAYLOAD_WEIGHT, COUNTRIES);

            analyticsFilter.addItem("По годам", LAUNCH_COUNT, YEARS);
            analyticsFilter.addItem("По орбитам", LAUNCH_COUNT, ORBITS);
            analyticsFilter.addItem("По миссиям", LAUNCH_COUNT, MISSIONS);
            analyticsFilter.addItem("По странам", LAUNCH_COUNT, COUNTRIES);
            return analyticsFilter;
        });
    }

    private Throwable getError() {
        return new Throwable("do nothing");
    }
}
