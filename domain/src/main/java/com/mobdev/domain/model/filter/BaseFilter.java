package com.mobdev.domain.model.filter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

public class BaseFilter<T extends IBaseFilter, I extends IBaseFilterItem, H> implements IBaseFilter<T, I, H> {
    List<I> mItems;
    private PublishProcessor<T> mPublishProcessor;
    private Flowable<T> mFilterLive;

    public enum ItemType {}

    BaseFilter() {
        mItems = new ArrayList<>();
        mPublishProcessor = PublishProcessor.create();
        mFilterLive = Flowable.fromPublisher(mPublishProcessor);
    }

    BaseFilter(T filter) {
        this();
        mItems.addAll(filter.getItems());
    }

    BaseFilter(List<I> items) {
        this();
        mItems.addAll(items);
    }


    @Override
    public Flowable<T> getUpdatesLive() {
        return mFilterLive;
    }

    @Override
    public T getSelectedFilter() {
        List<I> list = new ArrayList<>();
        for (I item : mItems) {
            if (item.isSelected()) {
                list.add(item);
            }
        }
        return newInstance(list);
    }


    protected boolean addItem(I newItem) {
        boolean isFound = false;
        if (newItem != null) {
            for (I filter : mItems) {
                if (filter.equals(newItem)) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                mItems.add(newItem);
                notifySearchFilterChanges();
            }
        }
        return isFound;
    }

    @Override
    public int getItemsCount() {
        return mItems.size();
    }

    @Override
    public I getItem(int index) {
        if (checkIndex(index)) {
            return mItems.get(index);
        }
        return null;
    }

    private boolean checkIndex(int index) {
        if (index >= 0 || index < mItems.size() - 1) {
            return true;
        }
        throw new IllegalArgumentException("index out of bounds");
    }

    @Override
    public List<I> getItems() {
        return mItems;
    }

    void notifySearchFilterChanges() {
        mPublishProcessor.onNext((T) this);
    }

    @Override
    public T getFilterByType(H type) {
        List<I> list = new ArrayList<>();
        for (I item : mItems) {
            if (item.getType() == type) {
                list.add(item);
            }
        }
        return newInstance(list);
    }

    protected T newInstance(List<I> items) {
        return (T) new BaseFilter<T, I, H>(items);
    }

    protected class BaseFilterItem<S> implements IBaseFilterItem<S> {
        String mValue;
        boolean mIsSelected;
        S mType;

        BaseFilterItem(String value, S type) {
            mValue = value;
            mIsSelected = false;
            mType = type;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof BaseFilterItem
                    && mValue.contains(((BaseFilterItem) o).getValue())
                    && mType.equals(((BaseFilterItem) o).getType());
        }

        public String getValue() {
            return mValue;
        }

        public S getType() {
            return mType;
        }

        public boolean isSelected() {
            return mIsSelected;
        }

        public void setSelected(boolean selected) {
            if (mIsSelected != selected) {
                mIsSelected = selected;
                notifySearchFilterChanges();
            }
        }

        public void switchSelected() {
            setSelected(!mIsSelected);
        }
    }


}
