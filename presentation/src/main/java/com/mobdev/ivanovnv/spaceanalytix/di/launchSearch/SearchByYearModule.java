package com.mobdev.ivanovnv.spaceanalytix.di.launchSearch;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByLaunchYear;

import toothpick.config.Module;

public class SearchByYearModule extends Module {
    public SearchByYearModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByLaunchYear(launchService));
    }
}
