package com.example.domain.model.searchFilter;

public class LaunchSearchFilter {

    private String mValue;
    private LaunchSearchType mType;
    private boolean isSelected;

    public LaunchSearchFilter(String value, LaunchSearchType type) {
        mValue = value;
        mType = type;
        isSelected = false;
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
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
