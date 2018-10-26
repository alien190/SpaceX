package com.example.ivanovnv.spacex.Launch;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.DetailLaunchFragment.DetailLaunchFragment;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.common.LaunchLayoutManager;
import com.example.ivanovnv.spacex.di.launchFragment.LaunchFragmentModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;
import toothpick.Scope;
import toothpick.Toothpick;

public class LaunchFragment extends Fragment implements LaunchAdapter.OnItemClickListener {

    private static String TAG = LaunchFragment.class.getSimpleName();

    @BindView(R.id.swipelayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    @Inject
    LaunchViewModel viewModel;
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

        View view = inflater.inflate(R.layout.fr_launches_list, container, false);
        ButterKnife.bind(this, view);

        Scope scope = Toothpick.openScopes("Application", "LaunchFragment");
        scope.installModules(new LaunchFragmentModule(this));
        Toothpick.inject(this, scope);
        mRecyclerView.setLayoutManager(mLaunchLayoutManager);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        viewModel.getLaunches().observe(this, mAdapter::updateLaunches);
        viewModel.getOnRefreshListener().observe(this, mSwipeRefreshLayout::setOnRefreshListener);
        viewModel.getIsLoadData().observe(this, mSwipeRefreshLayout::setRefreshing);

        postponeEnterTransition();

        return view;
    }

    @Override
    public void onItemClick(int flightNumber, View sharedView) {
        //Toast.makeText(getActivity(), "" + flightNumber, Toast.LENGTH_SHORT).show();

        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragment_container, DetailLaunchFragment.newInstance(flightNumber))
//                .addToBackStack(DetailLaunchFragment.class.getSimpleName())
//                .commit();

        try {
            // mRecyclerView.setAdapter(null);
            //mRecyclerView.setLayoutManager(null);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .addSharedElement(sharedView,
                            ViewCompat.getTransitionName(sharedView))
                    .addToBackStack(TAG)
                    .replace(R.id.fragment_container, DetailLaunchFragment.newInstance(flightNumber))
                    .commit();
        } catch (Throwable throwable) {
            Timber.d(throwable);
        }
    }

    @Override
    public void onDestroy() {
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
        super.onDestroy();
    }

}
