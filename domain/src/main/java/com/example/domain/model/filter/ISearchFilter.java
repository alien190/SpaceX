package com.example.domain.model.filter;

import java.util.List;

public interface ISearchFilter extends IBaseFilter<ISearchFilter, ISearchFilterItem, ISearchFilter.ItemType> {

    void updateFilterFromRepository(ISearchFilter searchFilter);

    void setTextQuery(String query);

    String getTextQuery();

    void submitTextQuery(String query);

    boolean addItem(String value, ItemType type);

    void addItems(List<String> values, ItemType type);


    enum ItemType {
        BY_MISSION_NAME,
        BY_ROCKET_NAME,
        BY_LAUNCH_YEAR,
        BY_COUNTRY,
        BY_ORBIT,
        EMPTY
    }
}
