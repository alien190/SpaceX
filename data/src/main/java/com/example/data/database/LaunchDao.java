package com.example.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.data.model.DataLaunch;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLaunches(List<DataLaunch> dataLaunches);

    @Query("SELECT * FROM DataLaunch ORDER BY launch_date_unix")
    List<DataLaunch> getLaunches();

    @Query("SELECT * FROM DataLaunch ORDER BY launch_date_unix LIMIT 5")
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
    DataLaunch getLaunchByFlightNumber(int flightNumber);
}
