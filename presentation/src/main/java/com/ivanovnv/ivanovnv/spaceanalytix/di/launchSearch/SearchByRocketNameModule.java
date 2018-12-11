package com.ivanovnv.ivanovnv.spaceanalytix.di.launchSearch;

import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class SearchByRocketNameModule extends Module {
    public SearchByRocketNameModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByRocketName(launchService));
    }
}
