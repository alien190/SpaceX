package com.example.domain.model.searchFilter;

import java.util.List;

import io.reactivex.Flowable;

public interface IBaseFilter<T extends IBaseFilter, I extends IBaseFilterItem, H> {

    Flowable<T> getUpdatesLive();

    T getSelectedFilter();

    int getItemsCount();

    I getItem(int index);

    List<I> getItems();

    boolean addItem(String value, H type);

    void addItems(List<String> values, H type);

    T getFilterByType(H type);

}
