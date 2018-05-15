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
}
