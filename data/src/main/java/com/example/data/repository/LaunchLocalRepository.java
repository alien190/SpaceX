package com.example.data.repository;

import android.content.Context;

import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.data.R;
import com.example.data.model.DataFilterItem;
import com.example.data.utils.DataToDomainConverter;
import com.example.data.utils.DomainToDataConverter;
import com.example.data.database.LaunchDao;
import com.example.data.model.DataImage;
import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.filter.AnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilterItem;
import com.example.domain.model.filter.ISearchFilterItem;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.model.filter.SearchFilter;
import com.example.domain.repository.ILaunchRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.Single;
import timber.log.Timber;

import static com.example.domain.model.filter.IAnalyticsFilter.ItemType.LAUNCH_COUNT;
import static com.example.domain.model.filter.IAnalyticsFilter.ItemType.PAYLOAD_WEIGHT;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.COUNTRIES;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.MISSIONS;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.ORBITS;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.ROCKET;
import static com.example.domain.model.filter.IAnalyticsFilterItem.BaseType.YEARS;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_COUNTRY;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_LAUNCH_YEAR;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_MISSION_NAME;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_ORBIT;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_ROCKET_NAME;

public class LaunchLocalRepository implements ILaunchRepository {

    private LaunchDao mLaunchDao;

    private String mSearchByYearTitle;
    private String mSearchByOrbitTitle;
    private String mSearchByMissionTitle;
    private String mSearchByCountryTitle;
    private String mSearchByRocketTitle;

    private static final int BY_MISSION_NAME_INDEX = 1;
    private static final int BY_ROCKET_NAME_INDEX = 2;
    private static final int BY_LAUNCH_YEAR_INDEX = 3;
    private static final int BY_COUNTRY_INDEX = 4;
    private static final int BY_ORBIT_INDEX = 5;

    private static final HashMap<Integer, ISearchFilter.ItemType> mItemTypeByIndex =
            new HashMap<Integer, ISearchFilter.ItemType>() {
                {
                    put(BY_MISSION_NAME_INDEX, BY_MISSION_NAME);
                    put(BY_ROCKET_NAME_INDEX, BY_ROCKET_NAME);
                    put(BY_LAUNCH_YEAR_INDEX, BY_LAUNCH_YEAR);
                    put(BY_COUNTRY_INDEX, BY_COUNTRY);
                    put(BY_ORBIT_INDEX, BY_ORBIT);
                }
            };

    private static final HashMap<ISearchFilter.ItemType, String> mColumnNameByItemType =
            new HashMap<ISearchFilter.ItemType, String>() {
                {
                    put(BY_MISSION_NAME, "mission_name");
                    put(BY_ROCKET_NAME, "rocket_name");
                    put(BY_LAUNCH_YEAR, "launch_year");
                    put(BY_COUNTRY, "nationality");
                    put(BY_ORBIT, "orbit");
                }
            };

    private static final HashMap<IAnalyticsFilterItem.BaseType, String> mGroupByBaseType =
            new HashMap<IAnalyticsFilterItem.BaseType, String>() {
                {
                    put(COUNTRIES, "nationality");
                    put(MISSIONS, "mission_name");
                    put(ORBITS, "orbit");
                    put(YEARS, "launch_year");
                    put(ROCKET, "rocket_name");
                }
            };

    public LaunchLocalRepository(LaunchDao mLaunchDao, Context context) {
        this.mLaunchDao = mLaunchDao;
        mSearchByYearTitle = context.getString(R.string.search_by_year_title);
        mSearchByOrbitTitle = context.getString(R.string.search_by_orbit_title);
        mSearchByMissionTitle = context.getString(R.string.search_by_mission_title);
        mSearchByCountryTitle = context.getString(R.string.search_by_country_title);
        mSearchByRocketTitle = context.getString(R.string.search_by_rocket_title);
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
        String query = "SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id"
                + generateSqlFilterQuery(searchFilter, "AND")
                + "ORDER BY launch_date_unix DESC";
        Timber.d("SQL query: %s", query);
        return mLaunchDao
                .getLaunchesLiveWithFilter(new SimpleSQLiteQuery(query))
                .map(DataToDomainConverter::convertLaunchList);
    }

    @Override
    public Flowable<DomainAnalytics> getAnalyticsLive(ISearchFilter searchFilter, IAnalyticsFilter analyticsFilter) {
        IAnalyticsFilter analyticsFilterSelected = analyticsFilter.getSelectedFilter();
        if (analyticsFilterSelected.getItemsCount() == 1) {
            String groupByColumnName = mGroupByBaseType.get(analyticsFilterSelected.getItem(0).getBaseType());
            String query = "SELECT " +
                    generateSqlAnalyticsValue(analyticsFilterSelected)
                    + ", "
                    + groupByColumnName
                    + " as mBase FROM DataLaunch"
                    + generateSqlFilterQuery(searchFilter, "WHERE")
                    + "GROUP BY "
                    + groupByColumnName;
            Timber.d("SQL query: %s", query);
            return mLaunchDao
                    .getAnalyticsLive(new SimpleSQLiteQuery(query))
                    .map(dataAnalytics -> DataToDomainConverter.convertAnalytics(dataAnalytics, analyticsFilterSelected));
        }
        throw new IllegalArgumentException("filterSelected.getItemsCount() != 1");
    }

    private String generateSqlAnalyticsValue(IAnalyticsFilter analyticsFilterSelected) {
        switch (analyticsFilterSelected.getItem(0).getType()) {
            case PAYLOAD_WEIGHT: {
                return "sum(payload_mass_kg_sum) as mValue";
            }
            default: {
                return "count() as mValue";
            }
        }
    }

    String generateSqlFilterQuery(ISearchFilter searchFilter, String prefix) {
        ISearchFilter searchFilterCopy = new SearchFilter(searchFilter);
        String textQuery = searchFilterCopy.getTextQuery();
        if (textQuery != null && !textQuery.isEmpty()) {
            searchFilterCopy.addItem(textQuery, BY_MISSION_NAME);
        }

        String filter = generateSqlWhereConditions(searchFilterCopy);
        if (filter.isEmpty()) {
            filter = " ";
        } else {
            filter = " " + prefix + " (" + filter + ") ";
        }
        return filter;
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

    private String generateSqlWhereConditionsForType(ISearchFilter
                                                             searchFilter, ISearchFilter.ItemType type) {
        StringBuilder retValueBuilder = new StringBuilder();
        ISearchFilter filter = searchFilter.getFilterByType(type).getSelectedFilter();

        ISearchFilterItem item;
        int count = filter.getItemsCount();
        for (int i = 0; i < count; i++) {
            item = filter.getItem(i);
            if (item != null) {
                if (!retValueBuilder.toString().isEmpty()) {
                    retValueBuilder.append(" OR ");
                }
                retValueBuilder.append(mColumnNameByItemType.get(item.getType()));
                retValueBuilder.append(" LIKE '%");
                retValueBuilder.append(item.getValue());
                retValueBuilder.append("%'");
            }
        }
        return retValueBuilder.toString();
    }


    @Override
    public Flowable<ISearchFilter> getSearchFilterLive() {
        return mLaunchDao.getFilterListItems(
                BY_ROCKET_NAME_INDEX,
                BY_LAUNCH_YEAR_INDEX,
                BY_COUNTRY_INDEX,
                BY_ORBIT_INDEX)
                .map(this::getSearchFilter);
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

            initAnalyticsFilterByType(analyticsFilter, LAUNCH_COUNT);
            initAnalyticsFilterByType(analyticsFilter, PAYLOAD_WEIGHT);

            return analyticsFilter;
        });
    }

    private void initAnalyticsFilterByType(IAnalyticsFilter analyticsFilter, IAnalyticsFilter.ItemType type) {
        if (analyticsFilter != null && type != null) {
            analyticsFilter.addItem(mSearchByYearTitle, type, YEARS);
            analyticsFilter.addItem(mSearchByOrbitTitle, type, ORBITS);
            analyticsFilter.addItem(mSearchByMissionTitle, type, MISSIONS);
            analyticsFilter.addItem(mSearchByCountryTitle, type, COUNTRIES);
            analyticsFilter.addItem(mSearchByRocketTitle, type, ROCKET);
        } else {
            throw new IllegalArgumentException("analyticsFilter and type can't be null");
        }
    }

    private Throwable getError() {
        return new Throwable("do nothing");
    }
}
