package com.example.data.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.data.model.DataImage;
import com.example.data.model.DataLaunch;
import com.example.data.model.DataLaunchCache;

@Database(entities = {DataLaunch.class, DataLaunchCache.class, DataImage.class}, version = 1)
public abstract class LaunchDataBase extends RoomDatabase {
    public abstract LaunchDao getLaunchDao();

}
