package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.R;

public class SearchFilterListAdapter extends ListAdapter<LaunchSearchFilter, SearchFilterViewHolder> {

    private static final DiffUtil.ItemCallback<LaunchSearchFilter> DIFF_CALLBACK = new DiffUtil.ItemCallback<LaunchSearchFilter>() {
        @Override
        public boolean areItemsTheSame(@NonNull LaunchSearchFilter launchSearchFilter, @NonNull LaunchSearchFilter t1) {
            return launchSearchFilter.equals(t1);
        }

        @Override
        public boolean areContentsTheSame(@NonNull LaunchSearchFilter launchSearchFilter, @NonNull LaunchSearchFilter t1) {
            return launchSearchFilter.equals(t1);
        }
    };

    public SearchFilterListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public SearchFilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.li_search_filter, viewGroup, false);
        return new SearchFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilterViewHolder searchFilterViewHolder, int i) {
        searchFilterViewHolder.bind(getItem(i));
    }
}
