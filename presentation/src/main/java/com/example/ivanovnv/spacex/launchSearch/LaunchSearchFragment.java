package com.example.ivanovnv.spacex.launchSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.customComponents.SearchFilterLayoutManager;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterSelected;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Scope;
import toothpick.Toothpick;


public class LaunchSearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.search_recycler)
    RecyclerView mSearchRecycler;
    @BindView(R.id.btn_add_filter)
    ImageButton mAddSearchFilterButton;

    @Inject
    ILaunchSearchViewModel mSearchViewModel;
    @Inject
    SearchFilterLayoutManager mLayoutManager;
    @Inject
    SearchFilterAdapterSelected mListAdapter;


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
        mSearchRecycler.requestFocus();
        Scope scope = Toothpick.openScope("LaunchFragment");
        Toothpick.inject(this, scope);
        //mSearchViewModel.getSearchByNameQuery().observe(this, this::setSearchQuery);
        return view;
    }

    private void showEditDialogFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            String scopeName = "LaunchFragment";
            LaunchSearchChoiceDialogFragment launchSearchFilterDialogFragment =
                    LaunchSearchChoiceDialogFragment.newInstance(scopeName);
            launchSearchFilterDialogFragment.show(fragmentManager, scopeName);
        } else {
            throw new RuntimeException("getFragmentManager() return null");
        }
    }

    private void setSearchQuery(String newQuery) {
        if (newQuery == null) {
            mSearchView.setQuery("", false);
        } else {
            if (!newQuery.equals(String.valueOf(mSearchView.getQuery()))) {
                mSearchView.setQuery(newQuery, false);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //mListAdapter.setOnFilterItemClickListener(mSearchViewModel);
        mSearchView.setOnQueryTextListener(this);
        mSearchRecycler.setLayoutManager(mLayoutManager);
        mSearchRecycler.setAdapter(mListAdapter);
        mAddSearchFilterButton.setOnClickListener((view) -> showEditDialogFragment());
    }

    @Override
    public void onStop() {
        mSearchViewModel.submitTextQuery(mSearchView.getQuery().toString());
        //mListAdapter.setOnFilterItemClickListener(null);
        mSearchView.setOnQueryTextListener(null);
        mSearchRecycler.setLayoutManager(null);
        mSearchRecycler.setAdapter(null);
        mAddSearchFilterButton.setOnClickListener(null);
        super.onStop();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        mSearchView.setQuery("", false);
        mSearchViewModel.submitTextQuery(s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        mSearchViewModel.changeTextQuery(s);
        return false;
    }
}

