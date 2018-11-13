package com.example.domain.model.searchFilter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

public class SearchFilter implements ISearchFilter {
    private List<SearchFilterItem> mItems;
    private PublishProcessor<ISearchFilter> mPublishProcessor;
    private Flowable<ISearchFilter> mFilterLive;


    public SearchFilter() {
        mItems = new ArrayList<>();
        mPublishProcessor = PublishProcessor.create();
        mFilterLive = Flowable.fromPublisher(mPublishProcessor);
        notifySearchFilterChanges();
    }

    private SearchFilter(List<SearchFilterItem> items) {
        this();
        mItems.addAll(items);
        notifySearchFilterChanges();
    }

    @Override
    public Flowable<ISearchFilter> getUpdatesLive() {
        return mFilterLive;
    }

    @Override
    public ISearchFilter getSelectedFilter() {
        List<SearchFilterItem> list = new ArrayList<>();
        for (SearchFilterItem item : mItems) {
            if (item.isSelected()) {
                list.add(item);
            }
        }
        return new SearchFilter(list);
    }

    private void addItems(List<SearchFilterItem> newItems) {
        if (newItems != null) {
            for (SearchFilterItem item : newItems) {
                addItem(item);
            }
        }
    }

    private boolean addItem(SearchFilterItem newItem) {
        boolean isFound = false;
        if (newItem != null) {
            for (SearchFilterItem filter : mItems) {
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

    @Override
    public void addItems(List<String> values, ItemType type) {
        if (values != null) {
            for (String item : values) {
                addItem(item, type);
            }
        }
    }

    @Override
    public boolean addItem(String value, ItemType type) {
        if (value != null && !value.isEmpty()) {
            SearchFilterItem item = new SearchFilterItem(value, type);
            return addItem(item);
        }
        return false;
    }

    @Override
    public int getItemsCount() {
        return mItems.size();
    }

    @Override
    public String getItemValue(int index) {
        if (checkIndex(index)) {
            return mItems.get(index).getValue();
        }
        return "";
    }

    @Override
    public SearchFilterItem getItem(int index) {
        if (checkIndex(index)) {
            return mItems.get(index);
        }
        return null;
    }

    @Override
    public boolean getIsItemSelected(int index) {
        if (checkIndex(index)) {
            return mItems.get(index).isSelected();
        }
        return false;
    }

    @Override
    public void setIsItemSelected(int index) {
        setItemSelectedState(index, true);
    }

    @Override
    public void setIsItemUnselected(int index) {
        setItemSelectedState(index, false);
    }

    private void setItemSelectedState(int index, boolean state) {
        if (checkIndex(index)) {
            mItems.get(index).setSelected(state);
        }
    }

    @Override
    public void switchItemSelectedState(SearchFilterItem item) {
//        if (checkIndex(index)) {
//            mItems.get(index).switchSelected();
//        }
//        boolean c = mItems.contains(item);
//        item.switchSelected();
//        c = false;
        mItems.get(mItems.indexOf(item)).switchSelected();
        notifySearchFilterChanges();

    }

    private boolean checkIndex(int index) {
        if (index >= 0 || index < mItems.size() - 1) {
            return true;
        }
        throw new IllegalArgumentException("index out of bounds");
    }

    @Override
    public void updateFilterFromRepository(ISearchFilter searchFilter) {
        if (searchFilter instanceof SearchFilter) {
            mItems.clear();
            mItems.addAll(((SearchFilter) searchFilter).getItems());
            notifySearchFilterChanges();
        }
    }

    private List<SearchFilterItem> getItems() {
        return mItems;
    }

    private void notifySearchFilterChanges() {
        mPublishProcessor.onNext(this);
    }

    @Override
    public ISearchFilter getFilterByType(ItemType type) {
        List<SearchFilterItem> list = new ArrayList<>();
        for (SearchFilterItem item : mItems) {
            if (item.getType() == type) {
                list.add(item);
            }
        }
        return new SearchFilter(list);
    }


    public class SearchFilterItem {

        private String mValue;
        private ItemType mType;
        private boolean mIsSelected;

        private SearchFilterItem() {
            mValue = "";
            mIsSelected = false;
            mType = ItemType.EMPTY;
        }

        private SearchFilterItem(String value, ItemType type) {
            mValue = value;
            mType = type;
            mIsSelected = false;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof SearchFilterItem
                    && mType == ((SearchFilterItem) o).getType()
                    && mValue.equals(((SearchFilterItem) o).getValue());
        }

        private String getValue() {
            return mValue;
        }

        private void setValue(String value) {
            mValue = value;
        }

        private ItemType getType() {
            return mType;
        }

        private void setType(ItemType type) {
            mType = type;
        }

        private boolean isSelected() {
            return mIsSelected;
        }

        private void setSelected(boolean selected) {
            if (mIsSelected != selected) {
                mIsSelected = selected;
                notifySearchFilterChanges();
            }
        }

        private void switchSelected() {
            mIsSelected = !mIsSelected;
            notifySearchFilterChanges();
        }
    }
}
