package com.example.ivanovnv.spacex.di.application;

import android.content.Context;

import com.example.ivanovnv.spacex.currentPreferences.Converter;

import javax.inject.Inject;
import javax.inject.Provider;

class ConverterProvider implements Provider<Converter> {
    private Context mContext;

    @Inject
    public ConverterProvider(Context context) {
        mContext = context;
    }

    @Override
    public Converter get() {
        return new Converter(mContext);
    }
}
