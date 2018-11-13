package com.example.ivanovnv.spacex.currentPreferences;

import com.example.domain.model.searchFilter.SearchFilter;

public class CurrentPreferences implements ICurrentPreferences {
    private SearchFilter mSearchFilter;

    public CurrentPreferences() {
        mSearchFilter = new SearchFilter();
    }

    @Override
    public SearchFilter getSearchFilter() {
        return mSearchFilter;
    }
}
