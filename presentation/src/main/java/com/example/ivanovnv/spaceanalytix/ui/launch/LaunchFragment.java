package com.example.ivanovnv.spaceanalytix.ui.launch;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spaceanalytix.ui.launchDetail.LaunchDetailFragment;
import com.example.ivanovnv.spaceanalytix.R;
import com.example.ivanovnv.spaceanalytix.customComponents.LaunchLayoutManager;

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

        Scope scope = Toothpick.openScope( "LaunchFragment");
        Toothpick.inject(this, scope);

        View view = inflater.inflate(R.layout.fr_launches_list, container, false);
        ButterKnife.bind(this, view);

        mLaunchListViewModel.getLaunches().observe(this, mAdapter::updateLaunches);
        mLaunchListViewModel.getOnRefreshListener().observe(this, mSwipeRefreshLayout::setOnRefreshListener);
        mLaunchListViewModel.getIsLoadInProgress().observe(this, mSwipeRefreshLayout::setRefreshing);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setLayoutManager(mLaunchLayoutManager);
        mAdapter.setItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mRecyclerView.setLayoutManager(null);
        mAdapter.setItemClickListener(null);
        mRecyclerView.setAdapter(null);
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
                    .replace(R.id.fragment_container, LaunchDetailFragment.newInstance(flightNumber))
                    .addToBackStack(TAG)
                    .commit();
        }
    }

}
