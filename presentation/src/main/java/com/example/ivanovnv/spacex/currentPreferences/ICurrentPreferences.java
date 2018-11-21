package com.example.ivanovnv.spacex.currentPreferences;

import java.util.List;

public interface ICurrentPreferences {
    enum WeightUnitType {
        UNITS_SI,
        UNITS_ENG
    }

    void setIntegerValue(String key, String value);

    void setBooleanValue(String key, Boolean value);

    List<String> getWeightUnitSymbol();

    IConverter getConverter();

    WeightUnitType getUnit();

    boolean isUseLocalTime();

    boolean isLoadBigPictures();

}
