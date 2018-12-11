package com.ivanovnv.ivanovnv.spaceanalytix.ui.launchSearch;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ivanovnv.ivanovnv.spaceanalytix.R;
import com.ivanovnv.ivanovnv.spaceanalytix.customComponents.FilterLayoutManager;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterBySelected;

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

