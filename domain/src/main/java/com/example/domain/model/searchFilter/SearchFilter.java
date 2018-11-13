package com.example.domain.model.searchFilter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

public class SearchFilter implements ISearchFilter {
    private List<ISearchFilterItem> mItems;
    private PublishProcessor<ISearchFilter> mPublishProcessor;
    private Flowable<ISearchFilter> mFilterLive;


    public SearchFilter() {
        mItems = new ArrayList<>();
        mPublishProcessor = PublishProcessor.create();
        mFilterLive = Flowable.fromPublisher(mPublishProcessor);
        notifySearchFilterChages();
    }

    public SearchFilter(List<ISearchFilterItem> items) {
        this();
        mItems.addAll(items);
        notifySearchFilterChages();
    }


    @Override
    public void deleteItem(ISearchFilterItem item) {
        if (item != null) {
            mItems.remove(item);
            notifySearchFilterChages();
        }
    }

    @Override
    public List<ISearchFilterItem> getItems() {
        return mItems;
    }

    @Override
    public Flowable<ISearchFilter> getSearchFilterLive() {
        return mFilterLive;
    }

    @Override
    public ISearchFilter getSelectedFilter() {
        List<ISearchFilterItem> list = new ArrayList<>();
        for (ISearchFilterItem item : mItems) {
            if (item.isSelected()) {
                item.setSelected(false);
                list.add(item);
            }
        }
        return new SearchFilter(list);
    }

    @Override
    public ISearchFilter getCombinedFilter(ISearchFilter... filters) {
        ISearchFilter searchFilter = new SearchFilter();
        if (filters != null) {
            for (ISearchFilter item : filters) {
                searchFilter.addItems(item.getItems());
            }
        }
        return searchFilter;
    }

    @Override
    public void addItems(List<ISearchFilterItem> newItems) {
        if (newItems != null) {
            for (ISearchFilterItem item : newItems) {
                addItem(item);
            }
        }
    }

    @Override
    public boolean addItem(ISearchFilterItem newItem) {
        boolean isFound = false;
        if (newItem != null) {
            for (ISearchFilterItem filter : mItems) {
                if (filter.getType() == newItem.getType() && (filter.getValue().contains(newItem.getValue()))) {
                    isFound = true;
                    break;
                }
            }
            if (!isFound) {
                mItems.add(newItem);
                notifySearchFilterChages();
            }
        }
        return isFound;
    }

    @Override
    public void addItems(List<String> values, SearchFilterItemType type) {
        if (values != null) {
            for (String item : values) {
                addItem(item, type);
            }
        }
    }

    @Override
    public boolean addItem(String value, SearchFilterItemType type) {
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
            ISearchFilterItem searchFilterItem = mItems.get(index);
            if (searchFilterItem.isSelected() != state) {
                searchFilterItem.setSelected(state);
                notifySearchFilterChages();
            }
        }
    }

    private boolean checkIndex(int index) {
        if (index >= 0 || index < mItems.size() - 1) {
            return true;
        }
        throw new IllegalArgumentException("index out of bounds");
    }

    private void notifySearchFilterChages() {
        mPublishProcessor.onNext(this);
    }
}
