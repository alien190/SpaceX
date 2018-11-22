package com.example.ivanovnv.spaceanalytix.currentPreferences;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.ivanovnv.spaceanalytix.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentPreferences implements ICurrentPreferences {

    private Map<String, String> mStringPrefs;
    private Map<String, Boolean> mBooleanPrefs;
    private Integer[] mIntKeys = {R.string.unit_key};
    private Integer[] mBooleanKeys = {R.string.time_key, R.string.pictures_key};
    private String mUnitKey;
    private String mTimeKey;
    private String mPicturesKey;
    private List<String> mWeightUnitsSi;
    private List<String> mWeightUnitsEng;
    private IConverter mConverter;

    public CurrentPreferences(IConverter converter, Context context) {
        if (converter != null) {
            mConverter = converter;
            mConverter.setCurrentPreferences(this);
            init(context);
        } else {
            throw new IllegalArgumentException("converter  can't be null");
        }
    }

    private void init(Context context) {
        initPreferences(context);
        initKeys(context);
        initUnits(context);
    }

    private void initPreferences(Context context) {
        try {
            mStringPrefs = new HashMap<>();
            mBooleanPrefs = new HashMap<>();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            for (int id : mIntKeys) {
                String key = context.getString(id);
                mStringPrefs.put(key, sharedPreferences.getString(key, ""));
            }
            for (int id : mBooleanKeys) {
                String key = context.getString(id);
                mBooleanPrefs.put(key, sharedPreferences.getBoolean(key, true));
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void initKeys(Context context) {
        mUnitKey = context.getString(R.string.unit_key);
        mTimeKey = context.getString(R.string.time_key);
        mPicturesKey = context.getString(R.string.pictures_key);
    }

    private void initUnits(Context context) {
        mWeightUnitsSi = new ArrayList<>(Arrays.asList(
                context.getResources().getStringArray(R.array.weightUnitsSi)));
        mWeightUnitsEng = new ArrayList<>(Arrays.asList(
                context.getResources().getStringArray(R.array.weightUnitsEng)));
    }

    public void setIntegerValue(String key, String value) {
        try {
            if (key != null && !key.isEmpty() && value != null && !value.isEmpty()) {
                String oldValue = mStringPrefs.get(key);
                if (oldValue != value) {
                    mStringPrefs.put(key, value);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public void setBooleanValue(String key, Boolean value) {
        try {
            if (key != null && !key.isEmpty() && value != null) {
                boolean oldValue = mBooleanPrefs.get(key);
                if (oldValue != value) {
                    mBooleanPrefs.put(key, value);
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public boolean isUseLocalTime() {
        return getBooleanValue(mTimeKey);
    }

    @Override
    public boolean isLoadBigPictures() {
        return getBooleanValue(mPicturesKey);
    }

    public List<String> getWeightUnitSymbol() {
        if (getUnit() == WeightUnitType.UNITS_SI) {
            return mWeightUnitsSi;
        }
        return mWeightUnitsEng;
    }

    public WeightUnitType getUnit() {
        int value = getIntegerValueFromString(mUnitKey);
        return value >= 0 && value <= 1 ? WeightUnitType.values()[value] : WeightUnitType.UNITS_SI;
    }

    private int getIntegerValueFromString(String key) {
        try {
            return Integer.valueOf(mStringPrefs.get(key));
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return 0;
        }
    }

    private boolean getBooleanValue(String key) {
        try {
            return mBooleanPrefs.get(key);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
    }

    @Override
    public IConverter getConverter() {
        return mConverter;
    }

}
