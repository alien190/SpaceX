package com.example.ivanovnv.spacex.currentPreferences;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.SearchFilter;

public class CurrentPreferences implements ICurrentPreferences {
    private ISearchFilter mSearchFilter;

    public CurrentPreferences() {
        mSearchFilter = new SearchFilter();
    }

    @Override
    public ISearchFilter getSearchFilter() {
        return mSearchFilter;
    }
}
