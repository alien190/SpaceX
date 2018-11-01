package com.example.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.data.model.DataImage;
import com.example.data.model.DataLaunch;
import com.example.data.model.DataLaunchCache;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLaunchesCache(List<DataLaunchCache> dataLaunchCash);

//    @Query("SELECT DataLaunchCache.flight_number FROM DataLaunchCache LEFT JOIN DataLaunch " +
//            "ON DataLaunchCache.flight_number = DataLaunch.flight_number " +
//            "WHERE LENGTH(DataLaunch.image) IS NULL ORDER BY DataLaunchCache.flight_number DESC")

//    @Query("SELECT cacheTable.* FROM DataLaunch AS cacheTable LEFT JOIN DataLaunch AS nonCacheTable " +
//            "ON cacheTable.flight_number = nonCacheTable.flight_number " +
//            "WHERE cacheTable.isCache = 1 AND nonCacheTable.isCache = 0 " +
//            "AND (nonCacheTable.imageId =0 " +
//            "OR nonCacheTable.mission_patch_small <> cacheTable.mission_patch_small) " +
//            "ORDER BY flight_number DESC")
//    @Query("SELECT cacheTable.* FROM (SELECT * FROM DataLaunch WHERE isCache = 1) AS cacheTable " +
//            "LEFT JOIN (SELECT * FROM DataLaunch WHERE isCache = 0) AS nonCacheTable " +
//            "ON cacheTable.flight_number = nonCacheTable.flight_number " +
//            "AND (nonCacheTable.imageId =0 " +
//            "OR nonCacheTable.mission_patch_small <> cacheTable.mission_patch_small) " +
//            "ORDER BY launch_date_unix DESC")
//    Single<List<DataLaunch>> getLaunchFromCacheForUpdate();

    @Query("SELECT * FROM DataLaunch ORDER BY launch_date_unix DESC")
    List<DataLaunch> getLaunches();

    @Query("SELECT DataLaunch.*, DataImage.image FROM DataLaunch,DataImage WHERE DataLaunch.imageId=DataImage.id ORDER BY launch_date_unix DESC")
    Flowable<List<DataLaunch>> getLaunchesLive();


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

    @Query("SELECT * FROM DataLaunch where flight_number = :flightNumber")
    Single<DataLaunch> getLaunchByFlightNumber(int flightNumber);

    @Query("SELECT imageId FROM DataLaunch WHERE flight_number=:flight_number and mission_patch_small=:mission_patch_small")
    int getImageId(int flight_number, String mission_patch_small);

    @Query("DELETE FROM DataImage WHERE id <> 0 AND id " +
            "IN (SELECT DataImage.id FROM DataImage LEFT JOIN DataLaunch " +
            "ON DataImage.id = DataLaunch.imageId WHERE flight_number is NULL)")
    void deleteUnusedImages();
}
