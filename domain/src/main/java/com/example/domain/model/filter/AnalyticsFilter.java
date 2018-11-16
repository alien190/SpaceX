package com.example.domain.model.filter;

import java.util.List;

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


    private class AnalyticsFilterItem extends BaseFilterItem<IAnalyticsFilter.ItemType> implements IAnalyticsFilterItem {

        AnalyticsFilterItem(String value, IAnalyticsFilter.ItemType type) {
            super(value, type);
        }
    }

}
