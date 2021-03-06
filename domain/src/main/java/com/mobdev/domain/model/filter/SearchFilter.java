package com.mobdev.domain.model.filter;

import java.util.List;

import static com.mobdev.domain.model.filter.ISearchFilter.ItemType.BY_LAUNCH_YEAR;
import static com.mobdev.domain.model.filter.ISearchFilter.ItemType.BY_MISSION_NAME;
import static com.mobdev.domain.model.filter.ISearchFilter.ItemType.BY_ROCKET_NAME;

public class SearchFilter extends BaseFilter<ISearchFilter, ISearchFilterItem, ISearchFilter.ItemType> implements ISearchFilter {
    private String mTextQuery;

    public SearchFilter() {
        super();
    }

    public SearchFilter(ISearchFilter searchFilter) {
        super(searchFilter);
        mTextQuery = searchFilter.getTextQuery();
    }

    private SearchFilter(List<ISearchFilterItem> items) {
        super(items);
    }

    @Override
    protected boolean addItem(ISearchFilterItem newItem) {
        if (newItem.getType().equals(BY_MISSION_NAME)) {
            newItem.setSelected(true);
        }
        return super.addItem(newItem);
    }

    @Override
    protected ISearchFilter newInstance(List<ISearchFilterItem> items) {
        return new SearchFilter(items);
    }



    @Override
    public void updateFilterFromRepository(ISearchFilter searchFilter) {
        if (getFilterByType(BY_ROCKET_NAME).getItemsCount() !=
                searchFilter.getFilterByType(BY_ROCKET_NAME).getItemsCount() ||
                getFilterByType(BY_LAUNCH_YEAR).getItemsCount() !=
                        searchFilter.getFilterByType(BY_LAUNCH_YEAR).getItemsCount()) {
            mItems.clear();
            List<ISearchFilterItem> items = searchFilter.getItems();
            for (ISearchFilterItem item : items) {
                addItem(item.getValue(), item.getType());
            }
            notifySearchFilterChanges();
        }
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
            addItem(query, BY_MISSION_NAME);
            notifySearchFilterChanges();
        }
    }

    public void addItems(List<String> values, ISearchFilter.ItemType type) {
        if (values != null) {
            for (String item : values) {
                addItem(item, type);
            }
        }
    }

    public boolean addItem(String value, ISearchFilter.ItemType type) {
        if (value != null && !value.isEmpty()) {
            ISearchFilterItem item = new SearchFilterItem(value, type);
            return addItem(item);
        }
        return false;
    }

    private class SearchFilterItem extends BaseFilterItem<ISearchFilter.ItemType> implements ISearchFilterItem {

        SearchFilterItem(String value, ISearchFilter.ItemType type) {
            super(value, type);
        }

        @Override
        public void setSelected(boolean selected) {
            if (mIsSelected != selected) {
                mIsSelected = selected;
                if (mType == BY_MISSION_NAME) {
                    mItems.remove(this);
                }
                notifySearchFilterChanges();
            }
        }
    }
}
