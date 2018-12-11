package com.ivanovnv.domain.model.filter;

public interface IAnalyticsFilter extends IBaseFilter<IAnalyticsFilter, IAnalyticsFilterItem, IAnalyticsFilter.ItemType> {
    void updateFilterFromRepository(IAnalyticsFilter analyticsFilter);

    boolean addItem(String value, IAnalyticsFilter.ItemType type, IAnalyticsFilterItem.BaseType baseType);

    enum ItemType {
        PAYLOAD_WEIGHT,
        LAUNCH_COUNT
    }
}
