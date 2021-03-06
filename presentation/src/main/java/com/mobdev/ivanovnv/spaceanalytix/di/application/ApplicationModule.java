package com.mobdev.ivanovnv.spaceanalytix.di.application;

import android.content.Context;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.IConverter;
import com.mobdev.ivanovnv.spaceanalytix.customComponents.FilterLayoutManager;

import toothpick.config.Module;

public class ApplicationModule extends Module {

    public ApplicationModule(Context context) {
        bind(Context.class).toInstance(context);
        bind(ILaunchService.class).toProvider(LaunchServiceProvider.class).providesSingletonInScope();
        bind(FilterLayoutManager.class).to(FilterLayoutManager.class);
        bind(IConverter.class).toProvider(ConverterProvider.class).singletonInScope();
        bind(ICurrentPreferences.class).toProvider(CurrentPreferencesProvider.class).providesSingletonInScope();
    }
}
