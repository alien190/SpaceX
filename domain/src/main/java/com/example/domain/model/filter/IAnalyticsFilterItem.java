package com.example.domain.model.filter;

public interface IAnalyticsFilterItem extends IBaseFilterItem<IAnalyticsFilter.ItemType> {

    BaseType getBaseType();

    void setSelectedWithoutNotification(boolean selected);

    enum BaseType {
        YEARS,
        ORBITS,
        MISSIONS,
        COUNTRIES
    }
}
