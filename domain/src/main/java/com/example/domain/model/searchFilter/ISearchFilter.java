package com.example.domain.model.searchFilter;

import java.util.List;

public interface ISearchFilter extends IBaseFilter{

    boolean addItem(String value, ItemType type);

    void addItems(List<String> values, ItemType type);

    ISearchFilter getFilterByType(ItemType type);

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
