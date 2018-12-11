package com.ivanovnv.ivanovnv.spaceanalytix.di.launchSearch;

import com.ivanovnv.domain.service.ILaunchService;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.ivanovnv.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByOrbit;

import toothpick.config.Module;

public class SearchByOrbitModule extends Module {
    public SearchByOrbitModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByOrbit(launchService));
    }
}
