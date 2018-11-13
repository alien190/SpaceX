package com.example.domain.model.searchFilter;

public class SearchFilterItem {

    private String mValue;
    private SearchFilterItemType mType;
    private boolean mIsSelected;

    public SearchFilterItem() {
        mValue = "";
        mIsSelected = false;
        mType = SearchFilterItemType.EMPTY;
    }

    public SearchFilterItem(String value, SearchFilterItemType type) {
        mValue = value;
        mType = type;
        mIsSelected = false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SearchFilterItem
                && mType == ((SearchFilterItem) o).getType()
                && mValue == ((SearchFilterItem) o).getValue();
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public SearchFilterItemType getType() {
        return mType;
    }

    public void setType(SearchFilterItemType type) {
        mType = type;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
