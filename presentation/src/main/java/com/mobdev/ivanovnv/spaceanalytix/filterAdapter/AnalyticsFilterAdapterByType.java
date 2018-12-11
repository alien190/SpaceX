package com.mobdev.ivanovnv.spaceanalytix.filterAdapter;

import com.mobdev.domain.model.filter.IAnalyticsFilter;
import com.mobdev.domain.model.filter.IBaseFilterItem;
import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.R;

public class AnalyticsFilterAdapterByType extends AnalyticsFilterAdapter<BaseFilterViewHolder>
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
