package com.example.ivanovnv.spacex.filterAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.service.ILaunchService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class AnalyticsFilterAdapter<T extends BaseFilterViewHolder> extends BaseFilterAdapter<T> {

    private Disposable mFilterDisposable;

    public AnalyticsFilterAdapter(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        setFilter(mapSearchFilterUpdates(mLaunchService.getAnalyticsFilter()));
        mFilterDisposable =
                mLaunchService.getAnalyticsFilter()
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

    abstract IAnalyticsFilter mapSearchFilterUpdates(IAnalyticsFilter analyticsFilter);

    private void submitSearchFilter(IAnalyticsFilter analyticsFilter) {
        setFilter(analyticsFilter);
        notifyDataSetChanged();
    }
}
