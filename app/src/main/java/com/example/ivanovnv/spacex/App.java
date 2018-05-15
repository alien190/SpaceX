package com.example.ivanovnv.spacex;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.example.ivanovnv.spacex.DB.LaunchDataBase;
import com.example.ivanovnv.spacex.SpaceXAPI.APIutils;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

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
}


//new SingleObserver<List<Launch>>() {
//@Override
//public void onSubscribe(Disposable d) {
//        }
//
//@Override
//public void onSuccess(List<Launch> launches) {
//        try {
//        TimeUnit.SECONDS.sleep(5);
//        } catch (Throwable t) {
//        t.printStackTrace();
//        }
//        mDataBase.getLaunchDao().insertLaunches(launches);
//        Log.d("TAG", "onSuccess: insertLaunches");
//        }
//
//@Override
//public void onError(Throwable e) {
//        e.printStackTrace();
//        }
//        }