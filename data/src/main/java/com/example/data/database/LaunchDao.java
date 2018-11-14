package com.example.data.database;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;

import com.example.data.model.DataFilterItem;
import com.example.data.model.DataImage;
import com.example.data.model.DataLaunch;

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

    //@Query("SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id AND :filter ORDER BY launch_date_unix DESC")
    @RawQuery(observedEntities = {DataLaunch.class, DataImage.class})
    Flowable<List<DataLaunch>> getLaunchesLiveWithFilter(SupportSQLiteQuery query);

    @Query("SELECT * FROM DataLaunch WHERE flight_number < :lastFlightNumber ORDER BY launch_date_unix DESC LIMIT :returnCount")
    List<DataLaunch> getLaunchesInRange(int lastFlightNumber, int returnCount);

    @Query("SELECT COUNT(launch_year) AS count, " +
            "SUM(payload_mass_kg_sum) AS payload_mass_kg_sum, " +
            "SUM(payload_mass_lbs_sum) AS payload_mass_lbs_sum, " +
            "launch_year FROM DataLaunch GROUP BY launch_year")
    List<LaunchYearStatistic> getLaunchYearStatistic();

    @Query("SELECT COUNT(launch_year) AS count, " +
            "SUM(payload_mass_kg_sum) AS payload_mass_kg_sum, " +
            "SUM(payload_mass_lbs_sum) AS payload_mass_lbs_sum, " +
            "launch_year FROM DataLaunch WHERE launch_success = 0 GROUP BY launch_year")
    List<LaunchYearStatistic> getLaunchYearStatisticFailed();

    @Query("SELECT * FROM DataLaunch where launch_year = :year")
    List<DataLaunch> getLaunchesInYear(String year);

    @Query("SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id AND flight_number = :flightNumber")
    Single<DataLaunch> getLaunchByFlightNumber(int flightNumber);

    @Query("SELECT imageId FROM DataLaunch WHERE flight_number=:flight_number and mission_patch_small=:mission_patch_small")
    int getImageId(int flight_number, String mission_patch_small);

    @Query("DELETE FROM DataImage WHERE id <> 0 AND id " +
            "IN (SELECT DataImage.id FROM DataImage LEFT JOIN DataLaunch " +
            "ON DataImage.id = DataLaunch.imageId WHERE flight_number is NULL)")
    void deleteUnusedImages();

    @Query("SELECT rocket_name FROM DataLaunch GROUP BY rocket_name")
    Single<List<String>> getListRocketNames();

    @Query("SELECT launch_year FROM DataLaunch GROUP BY launch_year")
    Single<List<String>> getListLaunchYears();

    @Query("SELECT rocket_name as value, :rocket_name_index as type FROM DataLaunch GROUP BY rocket_name UNION SELECT launch_year as value, :launch_year_index as type FROM DataLaunch GROUP BY launch_year")
    Flowable<List<DataFilterItem>> getFilterListItems(int rocket_name_index, int launch_year_index);
}
