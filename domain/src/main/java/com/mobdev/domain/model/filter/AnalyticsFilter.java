package com.mobdev.domain.model.filter;

import java.util.List;

import static com.mobdev.domain.model.filter.IAnalyticsFilter.ItemType.LAUNCH_COUNT;
import static com.mobdev.domain.model.filter.IAnalyticsFilter.ItemType.PAYLOAD_WEIGHT;

public class AnalyticsFilter extends BaseFilter<IAnalyticsFilter, IAnalyticsFilterItem, IAnalyticsFilter.ItemType> implements IAnalyticsFilter {

    public AnalyticsFilter() {
        super();
    }

    public AnalyticsFilter(IAnalyticsFilter analyticsFilter) {
        super(analyticsFilter);
    }

    private AnalyticsFilter(List<IAnalyticsFilterItem> items) {
        super(items);
    }

    @Override
    public boolean addItem(String value, IAnalyticsFilter.ItemType type, IAnalyticsFilterItem.BaseType baseType) {
        if (value != null && !value.isEmpty()) {
            IAnalyticsFilterItem item = new AnalyticsFilterItem(value, type, baseType);
            return addItem(item);
        }
        return false;
    }

    @Override
    protected IAnalyticsFilter newInstance(List<IAnalyticsFilterItem> items) {
        return new AnalyticsFilter(items);
    }

    @Override
    public void updateFilterFromRepository(IAnalyticsFilter analyticsFilter) {
        if (getFilterByType(PAYLOAD_WEIGHT).getItemsCount() !=
                analyticsFilter.getFilterByType(PAYLOAD_WEIGHT).getItemsCount() ||
                getFilterByType(LAUNCH_COUNT).getItemsCount() !=
                        analyticsFilter.getFilterByType(LAUNCH_COUNT).getItemsCount()) {
            mItems.clear();
            List<IAnalyticsFilterItem> items = analyticsFilter.getItems();
            for (IAnalyticsFilterItem item : items) {
                addItem(item.getValue(), item.getType(), item.getBaseType());
            }
            if (mItems.size() != 0) {
                mItems.get(0).setSelected(true);
            }
            notifySearchFilterChanges();
        }
    }

    private void unselectAllItems() {
        for (IAnalyticsFilterItem item : mItems) {
            item.setSelectedWithoutNotification(false);
        }
    }

    private class AnalyticsFilterItem extends BaseFilterItem<IAnalyticsFilter.ItemType> implements IAnalyticsFilterItem {

        private BaseType mBaseType;

        AnalyticsFilterItem(String value, IAnalyticsFilter.ItemType type, BaseType baseType) {
            super(value, type);
            mBaseType = baseType;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof AnalyticsFilterItem
                    && mValue.contains(((AnalyticsFilterItem) o).getValue())
                    && mType.equals(((AnalyticsFilterItem) o).getType())
                    && mBaseType.equals(((AnalyticsFilterItem) o).getBaseType());
        }

        @Override
        public BaseType getBaseType() {
            return mBaseType;
        }

        @Override
        public void setSelectedWithoutNotification(boolean selected) {
            mIsSelected = selected;
        }

        @Override
        public void setSelected(boolean selected) {
            if (mIsSelected != selected) {
                unselectAllItems();
                mIsSelected = selected;
                notifySearchFilterChanges();
            }
        }
    }

}
