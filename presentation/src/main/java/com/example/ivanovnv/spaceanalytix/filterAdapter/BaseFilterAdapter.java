package com.example.ivanovnv.spaceanalytix.filterAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.filter.IBaseFilter;
import com.example.domain.model.filter.IBaseFilterItem;
import com.example.domain.service.ILaunchService;

public abstract class BaseFilterAdapter<T extends BaseFilterViewHolder> extends RecyclerView.Adapter<T> {

    private IOnFilterItemClickListener mOnItemClickListener;
    protected ILaunchService mLaunchService;
    private IBaseFilter mFilter;

    public BaseFilterAdapter(ILaunchService launchService) {
        mLaunchService = launchService;
    }

    @NonNull
    @Override
    public T onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getViewHolderLayoutResId(), viewGroup, false);
        return (T) new BaseFilterViewHolder(view);
    }

    abstract @LayoutRes
    int getViewHolderLayoutResId();

    @Override
    public void onBindViewHolder(@NonNull BaseFilterViewHolder searchFilterViewHolder, int i) {
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
