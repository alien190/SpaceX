package com.example.ivanovnv.spacex.launchSearch.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.ISearchFilterItem;
import com.example.domain.model.searchFilter.SearchFilter;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.launchSearch.SearchFilterViewHolder;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class BaseSearchFilterAdapter extends RecyclerView.Adapter<SearchFilterViewHolder> {

    private IOnFilterItemClickListener mOnItemClickListener;
    private Disposable mSearchFilterDisposable;
    private ISearchFilter mSearchFilter;
    protected ILaunchService mLaunchService;


    public BaseSearchFilterAdapter(ILaunchService launchService) {
        mLaunchService = launchService;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mSearchFilter = mapSearchFilterUpdates(mLaunchService.getSearchFilter());
        mSearchFilterDisposable =
                mLaunchService.getSearchFilter()
                        .getUpdatesLive()
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(this::mapSearchFilterUpdates)
                        .subscribe(this::submitSearchFilter, Timber::d);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mSearchFilterDisposable != null) {
            mSearchFilterDisposable.dispose();
        }
    }

    abstract ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter);

    @NonNull
    @Override
    public SearchFilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getViewHolderLayoutResId(), viewGroup, false);
        return new SearchFilterViewHolder(view);
    }

    abstract @LayoutRes
    int getViewHolderLayoutResId();

    @Override
    public void onBindViewHolder(@NonNull SearchFilterViewHolder searchFilterViewHolder, int i) {
        searchFilterViewHolder.bind(mSearchFilter.getItem(i), mOnItemClickListener);
    }

    @Override
    public int getItemCount() {
        return mSearchFilter.getItemsCount();
    }


    private void submitSearchFilter(ISearchFilter searchFilter) {
        mSearchFilter = searchFilter;
        notifyDataSetChanged();
    }

    public void setOnFilterItemClickListener(IOnFilterItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public interface IOnFilterItemClickListener {
        void onFilterItemClick(ISearchFilterItem item);
    }


}
