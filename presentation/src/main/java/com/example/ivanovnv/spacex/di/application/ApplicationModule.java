package com.example.ivanovnv.spacex.di.application;

import android.content.Context;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.currentPreferences.IConverter;
import com.example.ivanovnv.spacex.currentPreferences.Converter;
import com.example.ivanovnv.spacex.customComponents.FilterLayoutManager;

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
