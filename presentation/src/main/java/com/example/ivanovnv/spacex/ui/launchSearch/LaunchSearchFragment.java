package com.example.ivanovnv.spacex.ui.launchSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.customComponents.FilterLayoutManager;
import com.example.ivanovnv.spacex.filterAdapter.SearchFilterAdapterBySelected;

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
    FilterLayoutManager mLayoutManager;
    @Inject
    SearchFilterAdapterBySelected mListAdapter;

    private String mScopeName;


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
        mScopeName = "LaunchFragment";
        Scope scope = Toothpick.openScope(mScopeName);
        Toothpick.inject(this, scope);
        return view;
    }

    private void showEditDialogFragment() {
        SearchFilterChoiceDialogFragment.showDialogFragment(this, mScopeName);
    }

    @Override
    public void onStart() {
        super.onStart();
        mSearchView.setOnQueryTextListener(this);
        mSearchRecycler.setLayoutManager(mLayoutManager);
        mSearchRecycler.setAdapter(mListAdapter);
        mAddSearchFilterButton.setOnClickListener((view) -> showEditDialogFragment());
    }

    @Override
    public void onStop() {
        mSearchViewModel.submitTextQuery(mSearchView.getQuery().toString());
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

