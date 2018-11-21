package com.example.ivanovnv.spacex.currentPreferences;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import static com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences.WeightUnitType.UNITS_ENG;

public class WeightConverter implements IWeightConverter {
    private ICurrentPreferences mCurrentPreferences;

    @Inject
    public WeightConverter() {
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
}
