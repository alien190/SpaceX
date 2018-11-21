package com.example.ivanovnv.spacex.currentPreferences;

public interface IConverter {
    void setCurrentPreferences(ICurrentPreferences currentPreferences);

    float convertWeight(Float weightKilograms);

    String getWeightText(Float weightKilograms);

    String getTimeText(String timeUTC);
}