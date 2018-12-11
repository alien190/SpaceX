package com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter;

import com.ivanovnv.domain.model.filter.IBaseFilterItem;
import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.R;

public abstract class ChoiceSearchFilterAdapter extends SearchFilterAdapter
        implements BaseFilterAdapter.IOnFilterItemClickListener {

    public ChoiceSearchFilterAdapter(ILaunchService launchService) {
        super(launchService);
        setOnFilterItemClickListener(this);
    }

    @Override
    int getViewHolderLayoutResId() {
        return R.layout.li_search_filter_choice;
    }

    @Override
    public void onFilterItemClick(IBaseFilterItem item) {
        if (item != null) {
            item.switchSelected();
        }
    }
}
