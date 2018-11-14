package com.example.domain.model.searchFilter;

public interface ISearchFilterItem {
    String getValue();

    ISearchFilter.ItemType getType();

    boolean isSelected();

    void switchSelected();

    void setSelected(boolean selected);
}
