package com.example.ivanovnv.spaceanalytix.di.launch;

import androidx.lifecycle.ViewModelProviders;
import androidx.fragment.app.FragmentActivity;

import com.example.ivanovnv.spaceanalytix.ui.launch.LaunchViewModel;

import javax.inject.Inject;
import javax.inject.Provider;

public class LaunchViewModelProvider implements Provider<LaunchViewModel> {
    private FragmentActivity mActivity;
    private LaunchViewModelFactory mFactory;

    @Inject
    public LaunchViewModelProvider(FragmentActivity activity, LaunchViewModelFactory factory) {
        mActivity = activity;
        mFactory = factory;
    }

    @Override
    public LaunchViewModel get() {
        return ViewModelProviders.of(mActivity, mFactory).get(LaunchViewModel.class);
    }
}
