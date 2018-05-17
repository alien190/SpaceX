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

    @Query("SELECT * FROM launch")
    List<Launch> getLaunches();

    @Query("SELECT COUNT(launch_year) AS count, " +
            "SUM(payload_mass_kg_sum) AS payload_mass_kg_sum, " +
            "SUM(payload_mass_lbs_sum) AS payload_mass_lbs_sum, " +
            "launch_year FROM launch GROUP BY launch_year")
    List<LaunchYearStatistic> getLaunchYearStatistic();

    @Query("SELECT COUNT(launch_year) AS count, " +
            "SUM(payload_mass_kg_sum) AS payload_mass_kg_sum, " +
            "SUM(payload_mass_lbs_sum) AS payload_mass_lbs_sum, " +
            "launch_year FROM launch WHERE launch_success = 0 GROUP BY launch_year")
    List<LaunchYearStatistic> getLaunchYearStatisticFailed();

    @Query("SELECT * FROM launch where launch_year = :year")
    List<Launch> getLaunchesInYear(String year);
}
