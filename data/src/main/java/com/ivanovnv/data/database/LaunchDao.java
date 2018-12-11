package com.ivanovnv.data.database;

import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;

import com.ivanovnv.data.model.DataAnalytics;
import com.ivanovnv.data.model.DataFilterItem;
import com.ivanovnv.data.model.DataImage;
import com.ivanovnv.data.model.DataLaunch;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLaunches(List<DataLaunch> dataLaunches);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertLaunch(DataLaunch dataLaunch);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertImage(DataImage dataImage);

    @Query("SELECT * FROM DataLaunch ORDER BY launch_date_unix DESC")
    List<DataLaunch> getLaunches();

    @Query("SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id ORDER BY launch_date_unix DESC")
    Flowable<List<DataLaunch>> getLaunchesLive();

    @RawQuery(observedEntities = {DataLaunch.class, DataImage.class})
    Flowable<List<DataLaunch>> getLaunchesLiveWithFilter(SupportSQLiteQuery query);

    @RawQuery(observedEntities = {DataLaunch.class})
    Flowable<List<DataAnalytics>> getAnalyticsLive(SupportSQLiteQuery query);

    @Query("SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id AND flight_number = :flightNumber")
    Single<DataLaunch> getLaunchByFlightNumber(int flightNumber);

    @Query("SELECT imageId FROM DataLaunch WHERE flight_number=:flight_number and mission_patch_small=:mission_patch_small")
    int getImageId(int flight_number, String mission_patch_small);

    @Query("DELETE FROM DataImage WHERE id <> 0 AND id " +
            "IN (SELECT DataImage.id FROM DataImage LEFT JOIN DataLaunch " +
            "ON DataImage.id = DataLaunch.imageId WHERE flight_number is NULL)")
    void deleteUnusedImages();

    @Query("SELECT rocket_name as value, :rocket_name_index as type FROM DataLaunch GROUP BY rocket_name " +
            "UNION SELECT launch_year as value, :launch_year_index as type FROM DataLaunch GROUP BY launch_year " +
            "UNION SELECT nationality as value, :country_index as type FROM DataLaunch GROUP BY nationality " +
            "UNION SELECT orbit as value, :orbit_index as type FROM DataLaunch GROUP BY orbit")
    Flowable<List<DataFilterItem>> getFilterListItems(int rocket_name_index,
                                                      int launch_year_index,
                                                      int country_index,
                                                      int orbit_index);
}
