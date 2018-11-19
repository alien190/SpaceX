package com.example.ivanovnv.spacex.di.launchSearch;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.filterAdapter.BaseFilterAdapter;
import com.example.ivanovnv.spacex.filterAdapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class SearchByRocketNameModule extends Module {
    public SearchByRocketNameModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByRocketName(launchService));
    }
}
