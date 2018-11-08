package com.example.domain.model.searchFilter;

public class LaunchSearchFilter {

    private String mValue;
    private SearchType mType;

    public LaunchSearchFilter(String value, SearchType type) {
        mValue = value;
        mType = type;
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

    public SearchType getType() {
        return mType;
    }

    public void setType(SearchType type) {
        mType = type;
    }
}
