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

import java.util.List;

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


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLaunchAdapter = new LaunchAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLaunchAdapter);
        updateDatabaseFromServer();
    }

    @SuppressLint("CheckResult")
    void updateDatabaseFromServer() {
        APIutils.getApi().getAllPastLaunches()
                .flatMap((Function<List<Launch>, SingleSource<?>>) launches -> {
                    getLaunchDao().insertLaunches(launches);
                    return Single.just(launches);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(launches -> {
                    updateAdapterFromDataBase();}, Throwable::printStackTrace);
    }

    @SuppressLint("CheckResult")
    void updateAdapterFromDataBase() {
        Single.create((SingleOnSubscribe<List<Launch>>)
                emitter -> emitter.onSuccess(getLaunchDao().getLaunches()))
                .flatMap(mLaunchAdapter.updateFromDataBase)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    Toast.makeText(getActivity(), "" + integer, Toast.LENGTH_SHORT).show();
                }, Throwable::printStackTrace);


    }

    private LaunchDao getLaunchDao() {
        return ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }
}
