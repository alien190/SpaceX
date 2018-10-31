package com.example.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.data.model.DataLaunch;
import com.example.data.model.DataLaunchCache;

@Database(entities = {DataLaunch.class, DataLaunchCache.class}, version = 8)
public abstract class LaunchDataBase extends RoomDatabase {
    public abstract LaunchDao getLaunchDao();

}
