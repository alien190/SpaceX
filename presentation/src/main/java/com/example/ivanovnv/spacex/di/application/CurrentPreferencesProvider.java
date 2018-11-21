package com.example.ivanovnv.spacex.di.application;

import android.content.Context;

import com.example.ivanovnv.spacex.currentPreferences.CurrentPreferences;
import com.example.ivanovnv.spacex.currentPreferences.IWeightConverter;

import javax.inject.Inject;
import javax.inject.Provider;

class CurrentPreferencesProvider implements Provider<CurrentPreferences> {
    private IWeightConverter mWeightConverter;
    private Context mContext;

    @Inject
    public CurrentPreferencesProvider(IWeightConverter weightConverter, Context context) {
        mWeightConverter = weightConverter;
        mContext = context;
    }

    @Override
    public CurrentPreferences get() {
        return new CurrentPreferences(mWeightConverter, mContext);
    }
}
