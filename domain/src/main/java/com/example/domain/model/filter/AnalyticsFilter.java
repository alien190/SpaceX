package com.example.domain.model.filter;

import java.util.List;

import static com.example.domain.model.filter.IAnalyticsFilter.ItemType.LAUNCH_COUNT;
import static com.example.domain.model.filter.IAnalyticsFilter.ItemType.PAYLOAD_WEIGHT;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_LAUNCH_YEAR;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_MISSION_NAME;
import static com.example.domain.model.filter.ISearchFilter.ItemType.BY_ROCKET_NAME;

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
            notifySearchFilterChanges();
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
    }

}
