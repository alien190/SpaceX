package com.example.domain.model.searchFilter;

import java.util.ArrayList;
import java.util.List;

public class SearchFilter {
    private List<SearchFilterItem> mFilterItems;

    public SearchFilter() {
        mFilterItems = new ArrayList<>();
    }

    public SearchFilter(List<SearchFilterItem> filterItems) {
        this();
        mFilterItems.addAll(filterItems);
    }

    public boolean addFilterItem(String value, SearchFilterItemType type) {
        if (value != null && !value.isEmpty()) {
            SearchFilterItem item = new SearchFilterItem(value, type);
            return addFilterItem(item);
        }
        return false;
    }

    public boolean addFilterItem(SearchFilterItem newItem) {
        boolean isFound = false;
        if (newItem != null) {
            for (SearchFilterItem filter : mFilterItems) {
                if (filter.getType() == newItem.getType() && (filter.getValue().contains(newItem.getValue()))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                mFilterItems.add(newItem);
            }
        }
        return isFound;
    }

    private void removeFilterItem(SearchFilterItem item) {
        if (item != null) {
            mFilterItems.remove(item);
        }
    }

    public List<SearchFilterItem> getFilterItems() {
        return mFilterItems;
    }

    public void setFilterItems(List<SearchFilterItem> filterItems) {
        mFilterItems = filterItems;
    }
}
