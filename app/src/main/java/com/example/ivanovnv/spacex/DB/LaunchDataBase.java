package com.example.ivanovnv.spacex.DB;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

@Database(entities = {Launch.class}, version = 6)
public abstract class LaunchDataBase extends RoomDatabase{
    public abstract LaunchDao getLaunchDao();

}
