package com.example.ivanovnv.spacex.launchSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.customComponents.SearchFilterLayoutManager;
import com.example.ivanovnv.spacex.di.launchSearchFilter.LaunchSearchFilterFragmentModule;
import com.example.ivanovnv.spacex.launchSearchFilter.LaunchSearchFilterDialogFragment;

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
    SearchFilterLayoutManager mLayoutManager;
    @Inject
    SearchFilterAdapter mListAdapter;
    @Inject
    protected ItemTouchHelper mItemTouchHelper;


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

        mSearchViewModel.getSearchFilter().observe(this, mListAdapter::submitList);
        mSearchViewModel.getSearchByNameQuery().observe(this, this::setSearchQuery);
        mSearchViewModel.getSearchFilterItemForEdit().observe(this, this::showEditDialogFragment);

        return view;
    }

    private void showEditDialogFragment(LaunchSearchFilter launchSearchFilter) {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            String scopeName = "LaunchSearchFilter";
            Toothpick.closeScope(scopeName);
            Scope scope = Toothpick.openScopes("Application", scopeName);
            scope.installModules(new LaunchSearchFilterFragmentModule(this, launchSearchFilter));
            LaunchSearchFilterDialogFragment launchSearchFilterDialogFragment =
                    LaunchSearchFilterDialogFragment.newInstance(scopeName);
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
        mListAdapter.setOnFilterItemRemoveCallback(mSearchViewModel);
        mListAdapter.setOnFilterItemClickListener(mSearchViewModel);
        mSearchView.setOnQueryTextListener(mSearchViewModel);
        mSearchRecycler.setLayoutManager(mLayoutManager);
        mSearchRecycler.setAdapter(mListAdapter);
        mItemTouchHelper.attachToRecyclerView(mSearchRecycler);
    }

    @Override
    public void onStop() {
        mListAdapter.setOnFilterItemRemoveCallback(null);
        mListAdapter.setOnFilterItemClickListener(null);
        mSearchView.setOnQueryTextListener(null);
        mSearchRecycler.setLayoutManager(null);
        mSearchRecycler.setAdapter(null);
        mItemTouchHelper.attachToRecyclerView(null);
        super.onStop();
    }

}

