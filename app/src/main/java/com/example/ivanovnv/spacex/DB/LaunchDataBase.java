package com.example.ivanovnv.spacex.DB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

@Database(entities = {Launch.class}, version = 2)
public abstract class LaunchDataBase extends RoomDatabase{
    public abstract LaunchDao getLaunchDao();

}
