package com.example.ivanovnv.spacex.di.launchSearch;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.launchSearch.adapter.BaseFilterAdapter;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByLaunchYear;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class SearchByYearModule extends Module {
    public SearchByYearModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByLaunchYear(launchService));
    }
}
