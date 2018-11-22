package com.example.ivanovnv.spaceanalytix.filterAdapter;

import com.example.domain.model.filter.IBaseFilterItem;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.R;

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
