package com.example.domain.model.searchFilter;

import java.util.List;

import io.reactivex.Flowable;

public interface IBaseFilter<T> {

    boolean addItem(String value);

    void addItems(List<String> values);

    Flowable<T> getUpdatesLive();

    T getSelectedFilter();

    int getItemsCount();

    IBaseFilterItem getItem(int index);

}
