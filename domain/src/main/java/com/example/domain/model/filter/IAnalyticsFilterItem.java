package com.example.domain.model.filter;

public interface IAnalyticsFilterItem extends IBaseFilterItem<IAnalyticsFilter.ItemType> {

    BaseType getBaseType();

    enum BaseType {
        YEARS,
        ORBITS,
        MISSIONS,
        COUNTRIES
    }
}
