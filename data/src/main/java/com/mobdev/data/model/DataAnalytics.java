package com.mobdev.data.model;

public class DataAnalytics {
    private String mValue;
    private String mBase;

    public DataAnalytics(String value, String base) {
        mValue = value;
        mBase = base;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public String getBase() {
        return mBase;
    }

    public void setBase(String base) {
        mBase = base;
    }
}
