package com.mobdev.ivanovnv.spaceanalytix.filterAdapter;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdev.domain.model.filter.IAnalyticsFilter;
import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.R;

public class AnalyticsFilterAdapterBySelected extends AnalyticsFilterAdapter<AnalyticsFilterViewHolderBySelected> {
    public AnalyticsFilterAdapterBySelected(ILaunchService launchService) {
        super(launchService);
    }

    @Override
    IAnalyticsFilter mapSearchFilterUpdates(IAnalyticsFilter analyticsFilter) {
        return analyticsFilter.getSelectedFilter();
    }

    @Override
    int getViewHolderLayoutResId() {
        return R.layout.li_analytics_filter_selected;
    }

    @NonNull
    @Override
    public AnalyticsFilterViewHolderBySelected onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getViewHolderLayoutResId(), viewGroup, false);
        return new AnalyticsFilterViewHolderBySelected(view);
    }
}
