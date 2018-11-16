package com.example.ivanovnv.spacex.di.launchSearch;

import android.support.v7.widget.RecyclerView;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.launchSearch.adapter.BaseFilterAdapter;
import com.example.ivanovnv.spacex.launchSearch.adapter.SearchFilterAdapterByRocketName;

import toothpick.config.Module;

public class SearchByRocketNameModule extends Module {
    public SearchByRocketNameModule(ILaunchService launchService) {
        bind(BaseFilterAdapter.class).toInstance(new SearchFilterAdapterByRocketName(launchService));
    }
}
