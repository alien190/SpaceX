package com.example.domain.model.searchFilter;

public interface ISearchFilterItem {

    String getValue();

    void setValue(String value);

    SearchFilterItemType getType();

    void setType(SearchFilterItemType type);

    boolean isSelected();

    void setSelected(boolean selected);
}
