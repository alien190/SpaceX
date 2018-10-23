package com.example.ivanovnv.spacex.di.application;

import com.example.domain.repository.ILaunchRepository;
import com.example.domain.service.ILaunchService;
import com.example.domain.service.LaunchServiceImpl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

public class LaunchServiceProvider implements Provider<ILaunchService> {
    private ILaunchRepository mLaunchRepositoryLocal;
    private ILaunchRepository mLaunchRepositoryRemote;

    @Inject
    public LaunchServiceProvider(@Named(ILaunchRepository.LOCAL) ILaunchRepository launchRepositoryLocal,
                                 @Named(ILaunchRepository.REMOTE) ILaunchRepository launchRepositoryRemote) {
        mLaunchRepositoryLocal = launchRepositoryLocal;
        mLaunchRepositoryRemote = launchRepositoryRemote;
    }

    @Override
    public ILaunchService get() {
        return new LaunchServiceImpl(mLaunchRepositoryLocal, mLaunchRepositoryRemote);
    }
}
