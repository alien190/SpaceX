package com.example.domain.model.filter;

public interface IBaseFilterItem<H> {
    String getValue();

    boolean isSelected();

    void switchSelected();

    void setSelected(boolean selected);

    H getType();
}
