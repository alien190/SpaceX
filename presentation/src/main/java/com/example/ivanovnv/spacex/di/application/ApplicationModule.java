package com.example.ivanovnv.spacex.di.application;

import com.example.domain.service.ILaunchService;

import toothpick.config.Module;

public class ApplicationModule extends Module {
    public ApplicationModule() {
        bind(ILaunchService.class).toProvider(LaunchServiceProvider.class).providesSingletonInScope();
    }
}
