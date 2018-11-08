package com.example.domain.model.searchFilter;

public class LaunchSearchFilter {

    private String mValue;
    private SearchTypes mType;

    public LaunchSearchFilter(String value, SearchTypes type) {
        mValue = value;
        mType = type;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public SearchTypes getType() {
        return mType;
    }

    public void setType(SearchTypes type) {
        mType = type;
    }
}
