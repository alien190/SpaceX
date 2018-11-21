package com.example.ivanovnv.spacex.currentPreferences;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import static android.text.format.DateFormat.getDateFormat;
import static android.text.format.DateFormat.getTimeFormat;
import static com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences.WeightUnitType.UNITS_ENG;

public class Converter implements IConverter {
    private ICurrentPreferences mCurrentPreferences;
    private DateFormat mDateFormat;
    private DateFormat mTimeFormat;
    private TimeZone mLocalTimeZone;

    @Inject
    public Converter(Context context) {
        mDateFormat = getDateFormat(context);
        mTimeFormat = getTimeFormat(context);
        mLocalTimeZone = mDateFormat.getTimeZone();
    }

    public void setCurrentPreferences(ICurrentPreferences currentPreferences) {
        if (currentPreferences != null) {
            mCurrentPreferences = currentPreferences;
        } else {
            throw new IllegalArgumentException("currentPreferences can't be null");
        }
    }

    public String getWeightText(Float weightKilograms) {
        try {
            List<String> unitSymbols = mCurrentPreferences.getWeightUnitSymbol();
            String format = "%.1f " + unitSymbols.get(0);
            float weight = convertWeight(weightKilograms);
            return String.format(Locale.ENGLISH, format, weight);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return "";
        }
    }

    @Override
    public float convertWeight(Float weightKilograms) {
        float weight;
        if (weightKilograms == null || weightKilograms < 0) {
            weight = 0f;
        } else {
            weight = weightKilograms;
        }
        if (mCurrentPreferences.getUnit() == UNITS_ENG) {
            weight = weight / 0.45359237f;
        }
        return weight;
    }

    @Override
    public String getTimeText(String timeUTC) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
            if (mCurrentPreferences.isUseLocalTime()) {
                setTimeZone(mLocalTimeZone);
            } else {
                setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            Date date = formatter.parse(timeUTC);
            return mDateFormat.format(date) + " " + mTimeFormat.format(date);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return timeUTC;
        }
    }

    private void setTimeZone(TimeZone timeZone) {
        mDateFormat.setTimeZone(timeZone);
        mTimeFormat.setTimeZone(timeZone);
    }
}
