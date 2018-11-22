package com.example.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.data.utils.converter.StringListConverter;
import com.example.data.model.DataImage;
import com.example.data.model.DataLaunch;

@Database(entities = {DataLaunch.class, DataImage.class}, version = 1)
@TypeConverters(StringListConverter.class)
public abstract class LaunchDataBase extends RoomDatabase {
    public abstract LaunchDao getLaunchDao();

}
