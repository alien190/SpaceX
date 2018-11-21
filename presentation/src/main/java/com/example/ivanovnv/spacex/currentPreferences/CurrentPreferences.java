package com.example.ivanovnv.spacex.currentPreferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.ivanovnv.spacex.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentPreferences implements ICurrentPreferences {

    private Map mPrefs;
    private Integer[] mKeys = {R.string.unit_key};
    private String mUnitKey;
    private List<String> mWeightUnitsSi;
    private List<String> mWeightUnitsEng;
    private IWeightConverter mWeightConverter;

    public CurrentPreferences(IWeightConverter weightConverter, Context context) {
        if (weightConverter != null) {
            mWeightConverter = weightConverter;
            mWeightConverter.setCurrentPreferences(this);
            init(context);
        } else {
            throw new IllegalArgumentException("weightConverter can't be null");
        }
    }

    private void init(Context context) {
        initPreferences(context);
        initKeys(context);
        initUnits(context);
    }

    private void initPreferences(Context context) {
        mPrefs = new HashMap<String, String>();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        for (int id : mKeys) {
            String key = context.getString(id);
            mPrefs.put(key, sharedPreferences.getString(key, ""));
        }
    }

    private void initKeys(Context context) {
        mUnitKey = context.getString(R.string.unit_key);
    }

    private void initUnits(Context context) {
        mWeightUnitsSi = new ArrayList<>(Arrays.asList(
                context.getResources().getStringArray(R.array.weightUnitsSi)));
        mWeightUnitsEng = new ArrayList<>(Arrays.asList(
                context.getResources().getStringArray(R.array.weightUnitsEng)));
    }

    public void setValue(String key, String value) {
        if (key != null && !key.isEmpty() && value != null) {
            Object oldValue = mPrefs.get(key);
            if (oldValue == null || !oldValue.toString().equals(value)) {
                mPrefs.put(key, value);
            }
        }
    }

    public List<String> getWeightUnitSymbol() {
        if (getUnit() == WeightUnitType.UNITS_SI) {
            return mWeightUnitsSi;
        }
        return mWeightUnitsEng;
    }

    public WeightUnitType getUnit() {
        int value = getIntegerValue(mUnitKey);
        return value>=0 && value<=1 ? WeightUnitType.values()[value] : WeightUnitType.UNITS_SI;
    }

    private int getIntegerValue(String key) {
        Object value = mPrefs.get(key);
        if (value != null) {
            try {
                return Integer.valueOf(value.toString());
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return 0;
            }
        } else {
            return 0;
        }
    }

    public IWeightConverter getWeightConverter() {
        return mWeightConverter;
    }
}
