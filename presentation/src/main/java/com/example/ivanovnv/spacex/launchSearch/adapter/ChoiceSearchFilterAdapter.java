package com.example.ivanovnv.spacex.launchSearch.adapter;

import com.example.domain.model.searchFilter.ISearchFilterItem;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.R;

public abstract class ChoiceSearchFilterAdapter extends BaseSearchFilterAdapter
        implements BaseSearchFilterAdapter.IOnFilterItemClickListener {

    public ChoiceSearchFilterAdapter(ILaunchService launchService) {
        super(launchService);
        setOnFilterItemClickListener(this);
    }

    @Override
    int getViewHolderLayoutResId() {
        return R.layout.li_search_filter_choice;
    }

    @Override
    public void onFilterItemClick(ISearchFilterItem item) {
        if (item != null) {
            item.switchSelected();
        }
    }
}
