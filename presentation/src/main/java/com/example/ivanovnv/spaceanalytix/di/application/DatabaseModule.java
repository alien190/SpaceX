package com.example.ivanovnv.spaceanalytix.di.application;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.example.data.database.LaunchDataBase;
import com.example.data.repository.LaunchLocalRepository;
import com.example.data.utils.DbBitmapUtility;
import com.example.domain.repository.ILaunchRepository;
import com.example.ivanovnv.spaceanalytix.R;

import java.util.concurrent.Executors;

import toothpick.config.Module;

public class DatabaseModule extends Module {

    private LaunchDataBase mDataBase;
    private ILaunchRepository mLaunchRepositoryLocal;

    public DatabaseModule(Context context) {
        mDataBase = Room.databaseBuilder(context, LaunchDataBase.class, "launch_database")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            byte[] bytes = null;
                            Drawable drawable = context.getDrawable(R.drawable.ic_mission_icon_stub);
                            if (drawable instanceof BitmapDrawable) {
                                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                                bytes = DbBitmapUtility.getBytes(bitmapDrawable.getBitmap());
                            }
                            ContentValues values = new ContentValues();
                            values.put("image", bytes);
                            values.put("id", 0);
                            db.insert("DataImage", SQLiteDatabase.CONFLICT_IGNORE, values);
                        });
                    }
                })
                .fallbackToDestructiveMigration()
                .build();
        mLaunchRepositoryLocal = new LaunchLocalRepository(mDataBase.getLaunchDao());
        bind(ILaunchRepository.class).withName(ILaunchRepository.LOCAL).toInstance(mLaunchRepositoryLocal);
    }
}
