package com.example.domain.model.searchFilter;

import java.util.List;

import io.reactivex.Flowable;

public interface ISearchFilter {

    boolean addItem(String value, SearchFilterItemType type);

    boolean addItem(ISearchFilterItem newItem);

    void addItems(List<ISearchFilterItem> newItems);

    void addItems(List<String> values, SearchFilterItemType type);

    void deleteItem(ISearchFilterItem item);

    List<ISearchFilterItem> getItems();

    Flowable<ISearchFilter> getSearchFilterLive();

    ISearchFilter getSelectedFilter();

    ISearchFilter getCombinedFilter(ISearchFilter... filters);

    int getItemsCount();

    String getItemValue(int index);

    boolean getIsItemSelected(int index);

    void setIsItemSelected(int index);

    void setIsItemUnselected(int index);
}
