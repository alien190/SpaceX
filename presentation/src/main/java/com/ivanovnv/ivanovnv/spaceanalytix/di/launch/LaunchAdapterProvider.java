package com.ivanovnv.ivanovnv.spaceanalytix.di.launch;

import com.ivanovnv.ivanovnv.spaceanalytix.currentPreferences.ICurrentPreferences;
import com.ivanovnv.ivanovnv.spaceanalytix.ui.launch.LaunchAdapter;

import javax.inject.Inject;
import javax.inject.Provider;

class LaunchAdapterProvider implements Provider<LaunchAdapter> {
    private ICurrentPreferences mCurrentPreferences;

    @Inject
    public LaunchAdapterProvider(ICurrentPreferences currentPreferences) {
        mCurrentPreferences = currentPreferences;
    }

    @Override
    public LaunchAdapter get() {
        return new LaunchAdapter(mCurrentPreferences);
    }
}
