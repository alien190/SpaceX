package com.mobdev.ivanovnv.spaceanalytix.di.application;

import com.mobdev.domain.model.filter.ISearchFilter;
import com.mobdev.domain.service.ILaunchService;

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
