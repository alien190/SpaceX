package com.example.domain.model.searchFilter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class SearchFilter extends BaseFilter implements ISearchFilter {
    private String mTextQuery;
    private Flowable<SearchFilter> mFilterLive;
    private List<SearchFilterItem> mItems;


    public SearchFilter() {
        super();

    }

    public SearchFilter(ISearchFilter searchFilter) {
        super((BaseFilter) searchFilter);
    }

    private SearchFilter(List<SearchFilterItem> items) {
        super(items);
        // mItems.add
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
                if (newItem.getType() == ISearchFilter.ItemType.BY_MISSION_NAME) {
                    newItem.setSelected(true);
                }
                mItems.add(newItem);
                notifySearchFilterChanges();
            }
        }
        return isFound;
    }

    @Override
    public void addItems(List<String> values, ISearchFilter.ItemType type) {
        if (values != null) {
            for (String item : values) {
                addItem(item, type);
            }
        }
    }

    public boolean addItem(String value, ISearchFilter.ItemType type) {
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
    public void updateFilterFromRepository(ISearchFilter searchFilter) {
        if (searchFilter instanceof SearchFilter) {
            SearchFilter searchFilterCast = (SearchFilter) searchFilter;
            if (getFilterByType(ISearchFilter.ItemType.BY_ROCKET_NAME).getItemsCount() !=
                    searchFilterCast.getFilterByType(ISearchFilter.ItemType.BY_ROCKET_NAME).getItemsCount() ||
                    getFilterByType(ISearchFilter.ItemType.BY_LAUNCH_YEAR).getItemsCount() !=
                            searchFilterCast.getFilterByType(ISearchFilter.ItemType.BY_LAUNCH_YEAR).getItemsCount()) {
                mItems.clear();
                List<? extends BaseFilterItem> items = searchFilterCast.getItems();
                for (BaseFilterItem item : items) {
                    addItem(item.getValue(), ((SearchFilterItem) item).getType());
                }
                notifySearchFilterChanges();
            }
        }
    }

    public SearchFilter getFilterByType(ISearchFilter.ItemType type) {
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
            addItem(query, ISearchFilter.ItemType.BY_MISSION_NAME);
            notifySearchFilterChanges();
        }
    }

    private class SearchFilterItem extends BaseFilterItem implements IBaseFilterItem {

        private ISearchFilter.ItemType mType;

        private SearchFilterItem(String value, ISearchFilter.ItemType type) {
            mValue = value;
            mType = type;
            mIsSelected = false;
        }

        public ISearchFilter.ItemType getType() {
            return mType;
        }

        private void setType(ISearchFilter.ItemType type) {
            mType = type;
        }


        public void setSelected(boolean selected) {
            if (mIsSelected != selected) {
                mIsSelected = selected;
                if (mType == ISearchFilter.ItemType.BY_MISSION_NAME) {
                    mItems.remove(this);
                }
                notifySearchFilterChanges();
            }
        }

    }
}
