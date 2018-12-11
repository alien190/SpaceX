package com.ivanovnv.ivanovnv.spaceanalytix.di.launchSearch;

import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByLaunchYear;

import toothpick.config.Module;

public class SearchByYearModule extends Module {
    public SearchByYearModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByLaunchYear(launchService));
    }
}
