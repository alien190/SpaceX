package com.example.ivanovnv.spaceanalytix.di.launchSearch;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.example.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class SearchByRocketNameModule extends Module {
    public SearchByRocketNameModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByRocketName(launchService));
    }
}