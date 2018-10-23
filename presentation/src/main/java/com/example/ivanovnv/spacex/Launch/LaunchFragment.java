package com.example.ivanovnv.spacex.Launch;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.data.api.APIutils;
import com.example.data.database.LaunchDao;
import com.example.data.repository.LaunchLocalRepository;
import com.example.data.repository.LaunchRemoteRepository;
import com.example.domain.service.LaunchService;
import com.example.domain.service.LaunchServiceImpl;
import com.example.ivanovnv.spacex.App;
import com.example.ivanovnv.spacex.DetailLaunchFragment.DetailLaunchFragment;
import com.example.ivanovnv.spacex.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class LaunchFragment extends Fragment implements LaunchAdapter.OnItemClickListener {

    private static String TAG = LaunchFragment.class.getSimpleName();

    @BindView(R.id.swipelayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    LaunchViewModel viewModel;

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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        LaunchService launchService = new LaunchServiceImpl(new LaunchLocalRepository(getLaunchDao()),
                new LaunchRemoteRepository(APIutils.getApi()));

        viewModel = ViewModelProviders.of(this, new LaunchViewModelFactory(launchService))
                .get(LaunchViewModel.class);

        viewModel.getLaunchAdapter().observe(this, mRecyclerView::setAdapter);
        viewModel.getOnRefreshListener().observe(this, mSwipeRefreshLayout::setOnRefreshListener);
        viewModel.getIsLoadData().observe(this, mSwipeRefreshLayout::setRefreshing);

        return view;
    }


    private LaunchDao getLaunchDao() {
        return ((App) getActivity().getApplication()).getLaunchDataBase().getLaunchDao();
    }

    @Override
    public void onItemClick(int flightNumber) {
        //Toast.makeText(getActivity(), "" + flightNumber, Toast.LENGTH_SHORT).show();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DetailLaunchFragment.newInstance(flightNumber))
                .addToBackStack(DetailLaunchFragment.class.getSimpleName())
                .commit();
    }
}
