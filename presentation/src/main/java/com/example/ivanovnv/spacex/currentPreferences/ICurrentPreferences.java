package com.example.ivanovnv.spacex.currentPreferences;

import java.util.List;

public interface ICurrentPreferences {
    enum WeightUnitType {
        UNITS_SI,
        UNITS_ENG
    }

    void setValue(String key, String value);

    public List<String> getWeightUnitSymbol();

    IWeightConverter getWeightConverter();

    WeightUnitType getUnit();

}
