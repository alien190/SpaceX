package com.example.domain.model.searchFilter;

public interface IBaseFilterItem {
    String getValue();

    boolean isSelected();

    void switchSelected();

    void setSelected(boolean selected);
}
