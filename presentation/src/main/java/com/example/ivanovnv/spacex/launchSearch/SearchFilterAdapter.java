package com.example.ivanovnv.spacex.launchSearch;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.ISearchFilterItem;
import com.example.domain.model.searchFilter.SearchFilterItem;
import com.example.ivanovnv.spacex.R;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.launchSearch.touchHelper.ItemTouchAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class SearchFilterAdapter extends RecyclerView.Adapter<SearchFilterViewHolder> implements ItemTouchAdapter {

    private IOnFilterItemRemoveCallback mOnItemRemoveCallback;
    private IOnFilterItemClickListener mOnItemClickListener;
    private boolean mCanChoice;
    private Disposable mSearchFilterDisposable;
    private ISearchFilter mSearchFilter;

    @Inject
    ICurrentPreferences mCurrentPreferences;

    @Inject
    public SearchFilterAdapter() {
        mSearchFilter = mCurrentPreferences.getSearchFilter();
        mSearchFilterDisposable =
                mCurrentPreferences
                        .getSearchFilter()
                        .getSearchFilterLive()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::submitSearchFilter, Timber::d);
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
        searchFilterViewHolder.bind(i, mSearchFilter, mOnItemClickListener);
    }

//    ISearchFilterItem getItem(int index) {
//        if (checkPosition(index)) {
//            return mLaunchSearchFilterList.get(index);
//        } else {
//            return null;
//        }
//    }

    @Override
    public int getItemCount() {
        return mSearchFilter.getItemsCount();
    }

    @Override
    public void onItemDismiss(int position) {

//        if (checkPosition(position)) {
//            if (mOnItemRemoveCallback != null) {
//                mOnItemRemoveCallback.onFilterItemRemove(getItem(position));
//            }
//            mLaunchSearchFilterList.remove(position);
//            notifyItemRemoved(position);
//        }
    }

//    private boolean checkPosition(int position) {
//        return (position >= 0 && position < mLaunchSearchFilterList.size());
//    }

    private void submitSearchFilter(ISearchFilter searchFilter) {
        mSearchFilter = searchFilter;
        notifyDataSetChanged();
    }

    public void setOnFilterItemRemoveCallback(IOnFilterItemRemoveCallback onItemRemoveCallback) {
        mOnItemRemoveCallback = onItemRemoveCallback;
    }

    public void setOnFilterItemClickListener(IOnFilterItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface IOnFilterItemRemoveCallback {
        void onFilterItemRemove(SearchFilterItem item);
    }

    public interface IOnFilterItemClickListener {
        void onFilterItemClick(int index);

        //void onFilterItemCloseClick(SearchFilterItem item);
    }

    public void setCanChoice(boolean canChoice) {
        mCanChoice = canChoice;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mSearchFilterDisposable != null) {
            mSearchFilterDisposable.dispose();
        }
    }
}
