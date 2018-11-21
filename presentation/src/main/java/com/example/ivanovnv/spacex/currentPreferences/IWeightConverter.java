package com.example.ivanovnv.spacex.currentPreferences;

public interface IWeightConverter {
    void setCurrentPreferences(ICurrentPreferences currentPreferences);

    float convertWeight(Float weightKilograms);

    String getWeightText(Float weightKilograms);
}
