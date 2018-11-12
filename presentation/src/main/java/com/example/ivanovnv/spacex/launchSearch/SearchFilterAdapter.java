package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.launchSearch.touchHelper.ItemTouchAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchFilterAdapter extends RecyclerView.Adapter<SearchFilterViewHolder> implements ItemTouchAdapter {

    private List<LaunchSearchFilter> mLaunchSearchFilterList;
    private IOnFilterItemRemoveCallback mOnItemRemoveCallback;
    private IOnFilterItemClickListener mOnItemClickListener;
    private boolean mCanChoice;

    @Inject
    public SearchFilterAdapter() {
        mLaunchSearchFilterList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SearchFilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        @LayoutRes int layoutResId;
        if (mCanChoice) {
            layoutResId = R.layout.li_search_filter_choice;
        } else {
            layoutResId = R.layout.li_search_filter_action;
        }
        view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutResId, viewGroup, false);
        return new SearchFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFilterViewHolder searchFilterViewHolder, int i) {
        searchFilterViewHolder.bind(getItem(i), mOnItemClickListener);
    }

    LaunchSearchFilter getItem(int index) {
        if (checkPosition(index)) {
            return mLaunchSearchFilterList.get(index);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return mLaunchSearchFilterList.size();
    }

    @Override
    public void onItemDismiss(int position) {
        if (checkPosition(position)) {
            if (mOnItemRemoveCallback != null) {
                mOnItemRemoveCallback.onFilterItemRemove(getItem(position));
            }
            mLaunchSearchFilterList.remove(position);
            notifyItemRemoved(position);
        }
    }

    private boolean checkPosition(int position) {
        return (position >= 0 && position < mLaunchSearchFilterList.size());
    }

    public void submitList(List<LaunchSearchFilter> searchFilterList) {
        mLaunchSearchFilterList.clear();
        if (searchFilterList != null) {
            mLaunchSearchFilterList.addAll(searchFilterList);
        }
        notifyDataSetChanged();
    }

    public void setOnFilterItemRemoveCallback(IOnFilterItemRemoveCallback onItemRemoveCallback) {
        mOnItemRemoveCallback = onItemRemoveCallback;
    }

    public void setOnFilterItemClickListener(IOnFilterItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface IOnFilterItemRemoveCallback {
        void onFilterItemRemove(LaunchSearchFilter item);
    }

    public interface IOnFilterItemClickListener {
        void onFilterItemClick(LaunchSearchFilter item);
        void onFilterItemCloseClick(LaunchSearchFilter item);
    }

    public void setCanChoice(boolean canChoice) {
        mCanChoice = canChoice;
    }
}
