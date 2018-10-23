package com.example.ivanovnv.spacex.di.application;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.data.database.LaunchDataBase;
import com.example.data.repository.LaunchLocalRepository;
import com.example.domain.repository.ILaunchRepository;

import toothpick.config.Module;

public class DatabaseModule extends Module {

    private LaunchDataBase mDataBase;
    private ILaunchRepository mLaunchRepositoryLocal;

    public DatabaseModule(Context context) {
        mDataBase = Room.databaseBuilder(context, LaunchDataBase.class, "launch_database")
                .fallbackToDestructiveMigration()
                .build();
        mLaunchRepositoryLocal = new LaunchLocalRepository(mDataBase.getLaunchDao());
        bind(ILaunchRepository.class).withName(ILaunchRepository.LOCAL).toInstance(mLaunchRepositoryLocal);
    }
}
