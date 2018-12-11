package com.mobdev.ivanovnv.spaceanalytix.di.launchSearch;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByCountry;

import toothpick.config.Module;

public class SearchByCountryModule extends Module {
    public SearchByCountryModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByCountry(launchService));
    }
}
