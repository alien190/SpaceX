package com.example.domain.model.searchFilter;

public interface IBaseFilterItem {
    String getValue();

    IBaseFilter.ItemType getType();

    boolean isSelected();

    void switchSelected();

    void setSelected(boolean selected);
}
