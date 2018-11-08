package com.example.ivanovnv.spacex.launchSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Scope;
import toothpick.Toothpick;


public class LaunchSearchFragment extends Fragment {

    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.search_recycler)
    RecyclerView mSearchRecycler;

    @Inject
    ILaunchSearchViewModel mSearchViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    SearchFilterListAdapter mListAdapter;


    public static LaunchSearchFragment newInstance() {
        Bundle args = new Bundle();

        LaunchSearchFragment fragment = new LaunchSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_search_launch, container, false);
        ButterKnife.bind(this, view);

        Scope scope = Toothpick.openScope("LaunchFragment");
        Toothpick.inject(this, scope);

        mSearchViewModel.getSearchFilter().observe(this, this::setSearchFilterList);

        return view;
    }

    private void setSearchFilterList(List<LaunchSearchFilter> searchFilterList) {
        if (searchFilterList != null && !searchFilterList.isEmpty()) {
            mSearchRecycler.setVisibility(View.VISIBLE);
            mSearchRecycler.requestLayout();
        } else {
            mSearchRecycler.setVisibility(View.GONE);
        }
        mListAdapter.submitList(searchFilterList);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSearchView.setOnQueryTextListener(mSearchViewModel);
        mSearchRecycler.setLayoutManager(mLayoutManager);
        mSearchRecycler.setAdapter(mListAdapter);
    }

    @Override
    public void onStop() {
        mSearchView.setOnQueryTextListener(null);
        mSearchRecycler.setLayoutManager(null);
        mSearchRecycler.setAdapter(null);
        super.onStop();
    }
}
