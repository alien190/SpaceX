package com.example.domain.model.searchFilter;

public interface ISearchFilter extends IBaseFilter<ISearchFilter, ISearchFilterItem, ISearchFilter.ItemType> {

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
