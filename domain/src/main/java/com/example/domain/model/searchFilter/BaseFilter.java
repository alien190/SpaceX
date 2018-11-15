package com.example.domain.model.searchFilter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

public class BaseFilter {
    private List<BaseFilterItem> mItems;
    private PublishProcessor<BaseFilter> mPublishProcessor;
    private Flowable<BaseFilter> mFilterLive;

    public enum ItemType {}

    public BaseFilter() {
        mItems = new ArrayList<>();
        mPublishProcessor = PublishProcessor.create();
        mFilterLive = Flowable.fromPublisher(mPublishProcessor);
        notifySearchFilterChanges();
    }

    public BaseFilter(BaseFilter filter) {
        this();
        //if (filter instanceof BaseFilter) {
//            BaseFilter newFilter = ((BaseFilter) searchFilter);
        mItems.addAll(filter.getItems());
        notifySearchFilterChanges();
        //}
    }

    private BaseFilter(List<BaseFilterItem> items) {
        this();
        mItems.addAll(items);
        notifySearchFilterChanges();
    }

    public Flowable<BaseFilter> getUpdatesLive() {
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

    private void addItems(List<BaseFilterItem> newItems) {
        if (newItems != null) {
            for (BaseFilterItem item : newItems) {
                addItem(item);
            }
        }
    }

    private boolean addItem(BaseFilterItem newItem) {
        boolean isFound = false;
        if (newItem != null) {
            for (BaseFilterItem filter : mItems) {
                if (filter.getType() == newItem.getType() && (filter.getValue().contains(newItem.getValue()))) {
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

    public void addItems(List<String> values, ItemType type) {
        if (values != null) {
            for (String item : values) {
                addItem(item, type);
            }
        }
    }

    public boolean addItem(String value, ItemType type) {
        if (value != null && !value.isEmpty()) {
            BaseFilterItem item = new BaseFilterItem(value, type);
            return addItem(item);
        }
        return false;
    }

    public int getItemsCount() {
        return mItems.size();
    }

    public BaseFilterItem getItem(int index) {
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

    public void updateFilterFromRepository(ISearchFilter searchFilter) {
    }

    private List<BaseFilterItem> getItems() {
        return mItems;
    }

    private void notifySearchFilterChanges() {
        mPublishProcessor.onNext(this);
    }

    public BaseFilter getFilterByType(ItemType type) {
        List<BaseFilterItem> list = new ArrayList<>();
        for (BaseFilterItem item : mItems) {
            if (item.getType() == type) {
                list.add(item);
            }
        }
        return new BaseFilter(list);
    }


    private class BaseFilterItem {

        private String mValue;
        private ItemType mType;
        private boolean mIsSelected;

        private BaseFilterItem(String value, ItemType type) {
            mValue = value;
            mType = type;
            mIsSelected = false;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof BaseFilterItem
                    && mType == ((BaseFilterItem) o).getType()
                    && mValue.equals(((BaseFilterItem) o).getValue());
        }

        public String getValue() {
            return mValue;
        }

        private void setValue(String value) {
            mValue = value;
        }

        public ItemType getType() {
            return mType;
        }

        private void setType(ItemType type) {
            mType = type;
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
