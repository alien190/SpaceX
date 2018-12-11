package com.mobdev.ivanovnv.spaceanalytix.di.launchSearch;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class SearchByRocketNameModule extends Module {
    public SearchByRocketNameModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByRocketName(launchService));
    }
}
