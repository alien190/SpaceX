package com.example.ivanovnv.spacex.launch;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.detailLaunch.DetailLaunchFragment;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.customComponents.LaunchLayoutManager;
import com.example.ivanovnv.spacex.di.launch.LaunchFragmentModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Scope;
import toothpick.Toothpick;

public class LaunchFragment extends Fragment implements LaunchAdapter.OnItemClickListener {

    private static String TAG = LaunchFragment.class.getSimpleName();

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;


    @Inject
    ILaunchListViewModel mLaunchListViewModel;
    @Inject
    LaunchAdapter mAdapter;
    @Inject
    LaunchLayoutManager mLaunchLayoutManager;

    public static LaunchFragment newInstance() {
        Bundle args = new Bundle();
        LaunchFragment fragment = new LaunchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Scope scope = Toothpick.openScopes("Application", "LaunchFragment");
        scope.installModules(new LaunchFragmentModule(this));
        Toothpick.inject(this, scope);

        View view = inflater.inflate(R.layout.fr_launches_list, container, false);
        ButterKnife.bind(this, view);

        mLaunchListViewModel.getLaunches().observe(this, mAdapter::updateLaunches);
        mLaunchListViewModel.getOnRefreshListener().observe(this, mSwipeRefreshLayout::setOnRefreshListener);
        mLaunchListViewModel.getIsLoadInProgress().observe(this, mSwipeRefreshLayout::setRefreshing);

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        mRecyclerView.setLayoutManager(mLaunchLayoutManager);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        mRecyclerView.setLayoutManager(null);
        mAdapter.setItemClickListener(null);
        mRecyclerView.setAdapter(null);
        super.onStop();
    }

    @Override
    public void onItemClick(int flightNumber, View sharedView) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true);
            if (sharedView != null) {
                fragmentTransaction
                        .addSharedElement(sharedView, sharedView.getTransitionName());
            }
            fragmentTransaction
                    .replace(R.id.fragment_container, DetailLaunchFragment.newInstance(flightNumber))
                    .addToBackStack(TAG)
                    .commit();
        }
    }

}
