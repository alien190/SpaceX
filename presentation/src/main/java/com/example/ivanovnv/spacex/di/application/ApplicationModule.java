package com.example.ivanovnv.spacex.di.application;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.CurrentPreferences;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.customComponents.FilterLayoutManager;

import toothpick.config.Module;

public class ApplicationModule extends Module {
    private CurrentPreferences mCurrentPreferences;

    public ApplicationModule() {
        mCurrentPreferences = new CurrentPreferences();
        bind(ILaunchService.class).toProvider(LaunchServiceProvider.class).providesSingletonInScope();
        bind(ICurrentPreferences.class).toInstance(mCurrentPreferences);
        bind(FilterLayoutManager.class).to(FilterLayoutManager.class);
    }
}
