package com.ivanovnv.ivanovnv.spaceanalytix.di.launchDetail;

import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.Fragment;

import com.ivanovnv.ivanovnv.spaceanalytix.ui.launchDetail.LaunchDetailViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class LaunchDetailViewModelProvider implements Provider<LaunchDetailViewModel> {
    private Fragment mFragment;
    private LaunchDetailViewModelFactory mFactory;

    @Inject
    public LaunchDetailViewModelProvider(Fragment fragment, LaunchDetailViewModelFactory factory) {
        mFragment = fragment;
        mFactory = factory;
    }

    @Override
    public LaunchDetailViewModel get() {
        return ViewModelProviders.of(mFragment, mFactory).get(LaunchDetailViewModel.class);
    }
}
