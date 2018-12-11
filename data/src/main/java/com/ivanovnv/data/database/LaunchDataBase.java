package com.ivanovnv.data.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ivanovnv.data.utils.StringListConverter;
import com.ivanovnv.data.model.DataImage;
import com.ivanovnv.data.model.DataLaunch;

@Database(entities = {DataLaunch.class, DataImage.class}, version = 1)
@TypeConverters(StringListConverter.class)
public abstract class LaunchDataBase extends RoomDatabase {
    public abstract LaunchDao getLaunchDao();

}
