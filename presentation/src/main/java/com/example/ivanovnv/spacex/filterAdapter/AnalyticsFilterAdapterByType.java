package com.example.ivanovnv.spacex.filterAdapter;

import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IBaseFilterItem;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

public class AnalyticsFilterAdapterByType extends AnalyticsFilterAdapter
        implements BaseFilterAdapter.IOnFilterItemClickListener {
    private IAnalyticsFilter.ItemType mItemType;

    public AnalyticsFilterAdapterByType(ILaunchService launchService, IAnalyticsFilter.ItemType itemType) {
        super(launchService);
        mItemType = itemType;
        setOnFilterItemClickListener(this);
    }

    @Override
    IAnalyticsFilter mapSearchFilterUpdates(IAnalyticsFilter analyticsFilter) {
        return analyticsFilter.getFilterByType(mItemType);
    }

    @Override
    int getViewHolderLayoutResId() {
        return R.layout.li_search_filter_choice;
    }

    @Override
    public void onFilterItemClick(IBaseFilterItem item) {
        if (item != null && !item.isSelected()) {
            item.setSelected(true);
        }
    }
}
