package com.example.domain.model.analytics;

import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilterItem;

import java.util.List;

public class DomainAnalytics {
    private List<DomainAnalyticsItem> mItems;
    private IAnalyticsFilter.ItemType mItemType;
    private IAnalyticsFilterItem.BaseType mBaseType;

    public List<DomainAnalyticsItem> getItems() {
        return mItems;
    }

    public void setItems(List<DomainAnalyticsItem> items) {
        mItems = items;
    }

    public IAnalyticsFilter.ItemType getItemType() {
        return mItemType;
    }

    public void setItemType(IAnalyticsFilter.ItemType itemType) {
        mItemType = itemType;
    }

    public IAnalyticsFilterItem.BaseType getBaseType() {
        return mBaseType;
    }

    public void setBaseType(IAnalyticsFilterItem.BaseType baseType) {
        mBaseType = baseType;
    }
}
