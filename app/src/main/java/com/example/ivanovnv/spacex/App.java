package com.example.ivanovnv.spacex;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ivanovnv.spacex.DB.LaunchDataBase;
import com.example.ivanovnv.spacex.SpaceXAPI.APIutils;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.SafeObserver;
import io.reactivex.schedulers.Schedulers;


public class App extends Application {
    private LaunchDataBase mDataBase;
    private Observable<List<Launch>> mRefreshObservable;

    @Override
    public void onCreate() {
        super.onCreate();

        mDataBase = Room.databaseBuilder(getApplicationContext(), LaunchDataBase.class, "launch_database")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                .build();


        mRefreshObservable = new Observable<List<Launch>>() {
            @Override
            protected void subscribeActual(Observer<? super List<Launch>> observer) {

            }
        };
//        mRefreshObservable = Observable.create(emitter -> APIutils.getApi().getAllPastLaunches()
//                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(launches -> emitter.onNext(launches)));


//        Disposable disposable = Observable.create(emitter -> mEmitter = emitter)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .flatMapSingle((Function<Object, SingleSource<List<Launch>>>) o -> APIutils.getApi().getAllPastLaunches())
//                .subscribe(launches -> {
//                    try {
//                        TimeUnit.SECONDS.sleep(5);
//                    } catch (Throwable t) {
//                        t.printStackTrace();
//                    }
//                    mDataBase.getLaunchDao().insertLaunches(launches);
//                    Log.d("TAG", "onSuccess: insertLaunches thread:" + Thread.currentThread().getId());
//                }, Throwable::printStackTrace);


    }

    public LaunchDataBase getLaunchDataBase() {
        return mDataBase;
    }

//    public void updateDatabase() {
//        mEmitter.onNext(new Object());
//    }

    public Observable<List<Launch>> getRefreshObservable() {
        return mRefreshObservable;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE launch ADD COLUMN payload_mass_kg_sum INTEGET NOT NULL DEFAULT 0");
                database.execSQL("ALTER TABLE launch ADD COLUMN payload_mass_lbs_sum REAL NOT NULL DEFAULT 0");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE launch ADD COLUMN launch_success INTEGER NOT NULL DEFAULT 0");
            } catch (Throwable t) {
                t.printStackTrace();
            }

        }
    };

    static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE launch ADD COLUMN mission_name TEXT");
            } catch (Throwable t) {
                t.printStackTrace();
            }

        }
    };

    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE launch ADD COLUMN details TEXT");
            } catch (Throwable t) {
                t.printStackTrace();
            }

        }
    };

    static final Migration MIGRATION_5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            try {
                database.execSQL("ALTER TABLE launch ADD COLUMN rocket_name TEXT");
                database.execSQL("ALTER TABLE launch ADD COLUMN launch_date_utc TEXT");
            } catch (Throwable t) {
                t.printStackTrace();
            }

        }
    };

}
