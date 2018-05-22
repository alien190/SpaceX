package com.example.ivanovnv.spacex.LaunchFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LaunchFragment extends Fragment {

    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    LaunchAdapter mLaunchAdapter;
    Subscription mSubscription;

    public static LaunchFragment newInstance() {

        Bundle args = new Bundle();

        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_launches_list, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.swipelayout);
        mRecyclerView = view.findViewById(R.id.rv_main);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateAdapterFromDataBaseFlowable();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLaunchAdapter = new LaunchAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLaunchAdapter);
        //updateDatabaseFromServer();

        int i = 1;
        //mSubscription.request(1);
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
    private void updateAdapterFromDataBaseFlowable() {
        Flowable.defer((Callable<Publisher<List<Launch>>>) ()
                -> Flowable.just(getLaunchDao().getLaunchesInRange(mLaunchAdapter.getLastLoadedFlightNumber(), 5)))
                .flatMap(mLaunchAdapter.updateFromDataBaseFlowable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        //s.request(1);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private LaunchDao getLaunchDao() {
        return ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }
}
