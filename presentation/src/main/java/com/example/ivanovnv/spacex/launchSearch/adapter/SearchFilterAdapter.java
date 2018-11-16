package com.example.ivanovnv.spacex.launchSearch.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.service.ILaunchService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class SearchFilterAdapter extends BaseFilterAdapter {

    private Disposable mFilterDisposable;

    public SearchFilterAdapter(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        setFilter(mapSearchFilterUpdates(mLaunchService.getSearchFilter()));
        mFilterDisposable =
                mLaunchService.getSearchFilter()
                        .getUpdatesLive()
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(this::mapSearchFilterUpdates)
                        .subscribe(this::submitSearchFilter, Timber::d);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        if (mFilterDisposable != null) {
            mFilterDisposable.dispose();
        }
    }

    abstract ISearchFilter mapSearchFilterUpdates(ISearchFilter searchFilter);

    private void submitSearchFilter(ISearchFilter searchFilter) {
        setFilter(searchFilter);
        notifyDataSetChanged();
    }
}
