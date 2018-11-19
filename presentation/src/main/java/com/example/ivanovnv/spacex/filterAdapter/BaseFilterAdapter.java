package com.example.ivanovnv.spacex.filterAdapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.filter.IBaseFilter;
import com.example.domain.model.filter.IBaseFilterItem;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.ui.launchSearch.FilterViewHolder;

public abstract class BaseFilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

    private IOnFilterItemClickListener mOnItemClickListener;
    protected ILaunchService mLaunchService;
    private IBaseFilter mFilter;

    public BaseFilterAdapter(ILaunchService launchService) {
        mLaunchService = launchService;
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getViewHolderLayoutResId(), viewGroup, false);
        return new FilterViewHolder(view);
    }

    abstract @LayoutRes
    int getViewHolderLayoutResId();

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder searchFilterViewHolder, int i) {
        if (mFilter != null) {
            searchFilterViewHolder.bind(mFilter.getItem(i), mOnItemClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mFilter != null ? mFilter.getItemsCount() : 0;
    }

    protected void setOnFilterItemClickListener(IOnFilterItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public interface IOnFilterItemClickListener {
        void onFilterItemClick(IBaseFilterItem item);
    }

    public void setFilter(IBaseFilter filter) {
        mFilter = filter;
    }
}
