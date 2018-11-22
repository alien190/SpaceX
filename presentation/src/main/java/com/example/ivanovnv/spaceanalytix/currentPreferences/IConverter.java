package com.example.ivanovnv.spaceanalytix.currentPreferences;

public interface IConverter {
    void setCurrentPreferences(ICurrentPreferences currentPreferences);

    float convertWeight(Float weightKilograms);

    String getWeightText(Float weightKilograms);

    String getTimeText(String timeUTC);
}