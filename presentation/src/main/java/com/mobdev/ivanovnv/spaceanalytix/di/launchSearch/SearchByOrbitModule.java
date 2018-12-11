package com.mobdev.ivanovnv.spaceanalytix.di.launchSearch;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.mobdev.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByOrbit;

import toothpick.config.Module;

public class SearchByOrbitModule extends Module {
    public SearchByOrbitModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByOrbit(launchService));
    }
}
