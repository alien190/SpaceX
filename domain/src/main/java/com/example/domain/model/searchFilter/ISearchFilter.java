package com.example.domain.model.searchFilter;

import java.util.List;

import io.reactivex.Flowable;

public interface ISearchFilter {

    boolean addItem(String value, ItemType type);

    void addItems(List<String> values, ItemType type);

    Flowable<ISearchFilter> getUpdatesLive();

    ISearchFilter getSelectedFilter();

    ISearchFilter getFilterByType(ItemType type);

    int getItemsCount();

    ISearchFilterItem getItem(int index);

    void updateFilterFromRepository(ISearchFilter searchFilter);

    void setTextQuery(String query);

    String getTextQuery();

    void submitTextQuery(String query);

    enum ItemType {
        BY_MISSION_NAME,
        BY_ROCKET_NAME,
        BY_LAUNCH_YEAR,
        EMPTY
    }
}
