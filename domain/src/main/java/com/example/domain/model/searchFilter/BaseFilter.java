package com.example.domain.model.searchFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

public class BaseFilter<T> implements IBaseFilter<T>{
    protected List<BaseFilterItem> mItems;
    protected PublishProcessor<BaseFilter> mPublishProcessor;
    protected Flowable<BaseFilter> mFilterLive;

    public enum ItemType {}

    protected BaseFilter() {
        mItems = new ArrayList<>();
        mPublishProcessor = PublishProcessor.create();
        mFilterLive = Flowable.fromPublisher(mPublishProcessor);
        notifySearchFilterChanges();
    }

    protected BaseFilter(BaseFilter filter) {
        this();
        //if (filter instanceof BaseFilter) {
//            BaseFilter newFilter = ((BaseFilter) searchFilter);
        mItems.addAll(filter.getItems());
        notifySearchFilterChanges();
        //}
    }

    protected BaseFilter(List<? extends BaseFilterItem> items) {
        this();
        mItems.addAll(items);
        notifySearchFilterChanges();
    }


    public Flowable<T> getUpdatesLive() {
        return mFilterLive;
    }

    public BaseFilter getSelectedFilter() {
        List<BaseFilterItem> list = new ArrayList<>();
        for (BaseFilterItem item : mItems) {
            if (item.isSelected()) {
                list.add(item);
            }
        }
        return new BaseFilter(list);
    }

    private boolean addItem(BaseFilterItem newItem) {
        boolean isFound = false;
        if (newItem != null) {
            for (BaseFilterItem filter : mItems) {
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

    public void addItems(List<String> values) {
        if (values != null) {
            for (String item : values) {
                addItem(item);
            }
        }
    }

    public boolean addItem(String value) {
        if (value != null && !value.isEmpty()) {
            BaseFilterItem item = new BaseFilterItem(value);
            return addItem(item);
        }
        return false;
    }

    public int getItemsCount() {
        return mItems.size();
    }

    public IBaseFilterItem getItem(int index) {
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


    protected List<? extends BaseFilterItem> getItems() {
        return mItems;
    }

    protected void notifySearchFilterChanges() {
        mPublishProcessor.onNext(this);
    }


    protected class BaseFilterItem implements IBaseFilterItem {

        protected String mValue;
        protected boolean mIsSelected;

        public BaseFilterItem() {
        }

        protected BaseFilterItem(String value) {
            mValue = value;
            mIsSelected = false;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof BaseFilterItem
                    && mValue.contains(((BaseFilterItem) o).getValue());
        }

        public String getValue() {
            return mValue;
        }

        private void setValue(String value) {
            mValue = value;
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
