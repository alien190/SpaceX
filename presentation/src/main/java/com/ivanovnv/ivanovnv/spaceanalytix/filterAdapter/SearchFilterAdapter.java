package com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivanovnv.domain.model.filter.ISearchFilter;
import com.ivanovnv.domain.service.ILaunchService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public abstract class SearchFilterAdapter extends BaseFilterAdapter<BaseFilterViewHolder> {

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
