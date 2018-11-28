package com.example.ivanovnv.spaceanalytix.di.launchSearch;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spaceanalytix.filterAdapter.BaseFilterAdapter;
import com.example.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByCountry;
import com.example.ivanovnv.spaceanalytix.filterAdapter.SearchFilterAdapterByOrbit;

import toothpick.config.Module;

public class SearchByOrbitModule extends Module {
    public SearchByOrbitModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByOrbit(launchService));
    }
}
