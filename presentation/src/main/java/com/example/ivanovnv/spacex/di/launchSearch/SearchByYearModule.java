package com.example.ivanovnv.spacex.di.launchSearch;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.filterAdapter.BaseFilterAdapter;
import com.example.ivanovnv.spacex.filterAdapter.SearchFilterAdapterByLaunchYear;

import toothpick.config.Module;

public class SearchByYearModule extends Module {
    public SearchByYearModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByLaunchYear(launchService));
    }
}
