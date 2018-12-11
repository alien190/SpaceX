package com.ivanovnv.domain.model.filter;

import java.util.List;

import io.reactivex.Flowable;

public interface IBaseFilter<T extends IBaseFilter, I extends IBaseFilterItem, H> {

    Flowable<T> getUpdatesLive();

    T getSelectedFilter();

    int getItemsCount();

    I getItem(int index);

    List<I> getItems();

    T getFilterByType(H type);
}
