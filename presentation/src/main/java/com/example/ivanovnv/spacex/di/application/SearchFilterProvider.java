package com.example.ivanovnv.spacex.di.application;

import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.service.ILaunchService;

import javax.inject.Inject;
import javax.inject.Provider;

public class SearchFilterProvider implements Provider<ISearchFilter> {
    private ILaunchService mLaunchService;

    @Inject
    public SearchFilterProvider(ILaunchService launchService) {
        mLaunchService = launchService;
    }

    @Override
    public ISearchFilter get() {
        return mLaunchService.getSearchFilter();
    }
}
