package com.ivanovnv.ivanovnv.spaceanalytix.di.launchSearch;

import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByCountry;

import toothpick.config.Module;

public class SearchByCountryModule extends Module {
    public SearchByCountryModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByCountry(launchService));
    }
}
