package com.mobdev.ivanovnv.spaceanalytix.di.application;

import android.content.Context;

import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.CurrentPreferences;
import com.mobdev.ivanovnv.spaceanalytix.currentPreferences.IConverter;

import javax.inject.Inject;
import javax.inject.Provider;

class CurrentPreferencesProvider implements Provider<CurrentPreferences> {
    private IConverter mWeightConverter;
    private Context mContext;

    @Inject
    public CurrentPreferencesProvider(IConverter weightConverter, Context context) {
        mWeightConverter = weightConverter;
        mContext = context;
    }

    @Override
    public CurrentPreferences get() {
        return new CurrentPreferences(mWeightConverter, mContext);
    }
}
