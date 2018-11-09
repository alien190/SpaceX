package com.example.domain.model.searchFilter;

public class LaunchSearchFilter {

    private String mValue;
    private LaunchSearchType mType;
    private boolean mIsSelected;

    public LaunchSearchFilter() {
        mValue = "";
        mIsSelected = false;
        mType = LaunchSearchType.EMPTY;
    }

    public LaunchSearchFilter(String value, LaunchSearchType type) {
        mValue = value;
        mType = type;
        mIsSelected = false;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof LaunchSearchFilter
                && mType == ((LaunchSearchFilter) o).getType()
                && mValue == ((LaunchSearchFilter) o).getValue();
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public LaunchSearchType getType() {
        return mType;
    }

    public void setType(LaunchSearchType type) {
        mType = type;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean selected) {
        mIsSelected = selected;
    }
}
