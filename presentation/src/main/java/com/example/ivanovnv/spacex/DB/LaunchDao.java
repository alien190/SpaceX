package com.example.ivanovnv.spacex.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

import java.util.List;

@Dao
public interface LaunchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLaunches(List<Launch> launches);

    @Query("SELECT * FROM Launch ORDER BY launch_date_unix DESC LIMIT 10")
    List<Launch> getLaunches();

    @Query("SELECT * FROM Launch WHERE flight_number < :lastFlightNumber ORDER BY launch_date_unix DESC LIMIT :returnCount")
    List<Launch> getLaunchesInRange(int lastFlightNumber, int returnCount);

    @Query("SELECT COUNT(launch_year) AS count, " +
            "SUM(payload_mass_kg_sum) AS payload_mass_kg_sum, " +
            "SUM(payload_mass_lbs_sum) AS payload_mass_lbs_sum, " +
            "launch_year FROM Launch GROUP BY launch_year")
    List<LaunchYearStatistic> getLaunchYearStatistic();

    @Query("SELECT COUNT(launch_year) AS count, " +
            "SUM(payload_mass_kg_sum) AS payload_mass_kg_sum, " +
            "SUM(payload_mass_lbs_sum) AS payload_mass_lbs_sum, " +
            "launch_year FROM Launch WHERE launch_success = 0 GROUP BY launch_year")
    List<LaunchYearStatistic> getLaunchYearStatisticFailed();

    @Query("SELECT * FROM Launch where launch_year = :year")
    List<Launch> getLaunchesInYear(String year);

    @Query("SELECT * FROM Launch where flight_number = :flightNumber")
    Launch getLaunchByFlightNumber(int flightNumber);
}
