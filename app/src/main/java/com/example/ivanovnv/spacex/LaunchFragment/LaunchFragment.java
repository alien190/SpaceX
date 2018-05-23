package com.example.ivanovnv.spacex.LaunchFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ivanovnv.spacex.App;
import com.example.ivanovnv.spacex.DB.LaunchDao;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.SpaceXAPI.APIutils;
import com.example.ivanovnv.spacex.SpaceXAPI.Launch;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LaunchFragment extends Fragment {

    private static String TAG = LaunchFragment.class.getSimpleName();
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    LaunchAdapter mLaunchAdapter;
    Subscription mSubscription;
    Flowable<List<Launch>> mFlowable;
    View view;


    public static LaunchFragment newInstance() {
        Bundle args = new Bundle();

        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.app_name));

        if (view == null) {
            view = inflater.inflate(R.layout.fr_launches_list, container, false);
            mSwipeRefreshLayout = view.findViewById(R.id.swipelayout);
            mRecyclerView = view.findViewById(R.id.rv_main);
            mLaunchAdapter = new LaunchAdapter();
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mLaunchAdapter);

            mFlowable = updateAdapterFromDataBaseFlowable();

            mFlowable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<Launch>>() {
                        @Override
                        public void onSubscribe(Subscription s) {
                            mSubscription = s;
                            s.request(1);
                            Log.d(TAG, "onSubscribe: ");
                        }

                        @Override
                        public void onNext(List<Launch> launches) {
                            Log.d(TAG, "onNext: launches.size: " + launches.size());
                            mLaunchAdapter.addLaunches(launches);
                        }

                        @Override
                        public void onError(Throwable t) {
                            t.printStackTrace();
                        }

                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onComplete: ");
                        }
                    });
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            if (mSubscription != null) mSubscription.request(1);
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void onStop() {
        super.onStop();
       // if (mSubscription != null) mSubscription.cancel();
        mSwipeRefreshLayout.setOnRefreshListener(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @SuppressLint("CheckResult")
    private void updateDatabaseFromServer() {
        APIutils.getApi().getAllPastLaunches()
                .flatMap((Function<List<Launch>, SingleSource<?>>) launches -> {
                    getLaunchDao().insertLaunches(launches);
                    return Single.just(launches);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(launches ->
                        updateAdapterFromDataBase(), Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    private void updateAdapterFromDataBase() {
        Single.create((SingleOnSubscribe<List<Launch>>)
                emitter -> emitter.onSuccess(getLaunchDao().getLaunches()))
                .flatMap(mLaunchAdapter.updateFromDataBase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    //Toast.makeText(getActivity(), "" + integer, Toast.LENGTH_SHORT).show();
                }, Throwable::printStackTrace);


    }

    @SuppressLint("CheckResult")
    private Flowable<List<Launch>> updateAdapterFromDataBaseFlowable() {
        return Flowable.create(emitter -> {
            int lastFlightNumber = Integer.MAX_VALUE;
            List<Launch> launches;

            for (launches = getLaunchDao().getLaunchesInRange(lastFlightNumber, 1);
                 !launches.isEmpty();
                 launches = getLaunchDao().getLaunchesInRange(lastFlightNumber, 1)) {
                lastFlightNumber = launches.get(0).getFlight_number();
                Log.d(TAG, "updateAdapterFromDataBaseFlowable: flightNumber: " + lastFlightNumber);
                emitter.onNext(launches);
            }
            emitter.onComplete();
        }, BackpressureStrategy.BUFFER);

//        return Flowable.defer((Callable<Publisher<List<Launch>>>) ()
//                -> {
//            Log.d(TAG, "updateAdapterFromDataBaseFlowable: ");
//            return Flowable.just(getLaunchDao().getLaunchesInRange(mLaunchAdapter.getLastLoadedFlightNumber(), 2));
//        }).flatMap((Function<List<Launch>, Publisher<Launch>>) launches -> Flowable.fromIterable(launches))
//                //.flatMap(mLaunchAdapter.updateFromDataBaseFlowable)
//                .buffer(1);
    }

    private LaunchDao getLaunchDao() {
        return ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }
}
