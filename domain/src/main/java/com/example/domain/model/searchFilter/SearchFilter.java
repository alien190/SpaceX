package com.example.domain.model.searchFilter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

public class SearchFilter implements ISearchFilter {
    private List<SearchFilterItem> mItems;
    private PublishProcessor<ISearchFilter> mPublishProcessor;
    private Flowable<ISearchFilter> mFilterLive;
    private String mTextQuery;


    public SearchFilter() {
        mItems = new ArrayList<>();
        mPublishProcessor = PublishProcessor.create();
        mFilterLive = Flowable.fromPublisher(mPublishProcessor);
        notifySearchFilterChanges();
    }

    public SearchFilter(ISearchFilter searchFilter) {
        this();
        if (searchFilter instanceof SearchFilter) {
            SearchFilter filter = ((SearchFilter) searchFilter);
            mItems.addAll(filter.getItems());
            mTextQuery = filter.mTextQuery;
            notifySearchFilterChanges();
        }
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
                if (newItem.getType() == ItemType.BY_MISSION_NAME) {
                    newItem.setSelected(true);
                }
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
    public ISearchFilterItem getItem(int index) {
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
    public void updateFilterFromRepository(ISearchFilter searchFilter) {
        if (searchFilter instanceof SearchFilter) {
            if (getFilterByType(ItemType.BY_ROCKET_NAME).getItemsCount() !=
                    searchFilter.getFilterByType(ItemType.BY_ROCKET_NAME).getItemsCount() ||
                    getFilterByType(ItemType.BY_LAUNCH_YEAR).getItemsCount() !=
                            searchFilter.getFilterByType(ItemType.BY_LAUNCH_YEAR).getItemsCount()) {
                mItems.clear();
                List<SearchFilterItem> items = ((SearchFilter) searchFilter).getItems();
                for (SearchFilterItem item : items) {
                    addItem(item.mValue, item.mType);
                }
                notifySearchFilterChanges();
            }
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

    @Override
    public void setTextQuery(String query) {
        if (query != null && !query.equals(mTextQuery)) {
            mTextQuery = query;
            notifySearchFilterChanges();
        }
    }

    @Override
    public String getTextQuery() {
        return mTextQuery;
    }

    @Override
    public void submitTextQuery(String query) {
        if (query != null && !query.isEmpty()) {
            mTextQuery = "";
            addItem(query, ItemType.BY_MISSION_NAME);
            notifySearchFilterChanges();
        }
    }

    private class SearchFilterItem implements ISearchFilterItem {

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
                if (mType == ItemType.BY_MISSION_NAME) {
                    mItems.remove(this);
                }
                notifySearchFilterChanges();
            }
        }

        public void switchSelected() {
            setSelected(!mIsSelected);
        }
    }
}
