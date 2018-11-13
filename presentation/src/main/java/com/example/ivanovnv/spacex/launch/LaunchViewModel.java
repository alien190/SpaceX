package com.example.ivanovnv.spacex.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.searchFilter.SearchFilterItem;
import com.example.domain.model.searchFilter.SearchFilterItemType;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.launchSearch.ILaunchSearchViewModel;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchViewModel extends ViewModel
        implements ILaunchListViewModel, ILaunchSearchViewModel, ILaunchSearchFilterCallback {

    private MutableLiveData<Boolean> mIsLoadData = new MutableLiveData<>();
    private MutableLiveData<SwipeRefreshLayout.OnRefreshListener> mOnRefreshListener = new MutableLiveData<>();
    private MutableLiveData<List<DomainLaunch>> mLaunches = new MutableLiveData<>();
    private MutableLiveData<List<SearchFilterItem>> mSearchFilter = new MutableLiveData<>();
    private MutableLiveData<String> mSearchByNameQuery = new MutableLiveData<>();
    private MutableLiveData<SearchFilterItem> mSearchFilterItemForEdit = new MutableLiveData<>();
    private ILaunchService mLaunchService;
    private ICurrentPreferences mICurrentPreferences;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable mLaunchLiveDisposable;


    public LaunchViewModel(ILaunchService launchService, ICurrentPreferences currentPreferences) {
        mLaunchService = launchService;
        mOnRefreshListener.postValue(this::refreshLaunches);
        loadLaunches(null);
    }


    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        mIsLoadData.postValue(false);
        super.onCleared();
    }


    private void refreshLaunches() {
        compositeDisposable.add(mLaunchService.refreshLaunches()
                .doOnSubscribe(s -> mIsLoadData.postValue(true))
                .doOnComplete(() -> mIsLoadData.postValue(false))
                .doOnError(throwable -> {
                    mIsLoadData.postValue(false);
                    Timber.d(throwable);
                })
                .subscribe(b -> mIsLoadData.postValue(false), Timber::d));
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        submitTextSearch(s);
        return true;
    }

    public void submitTextSearch() {
        submitTextSearch(mSearchByNameQuery.getValue());
    }

    public void submitTextSearch(String s) {
        List<SearchFilterItem> filterList = getCurrentSearchFilter();
        addNewFilterToList(filterList, s, SearchFilterItemType.BY_MISSION_NAME);
        mSearchFilter.postValue(filterList);
        mSearchByNameQuery.setValue("");
    }

    @Override
    public boolean onQueryTextChange(String s) {
        loadLaunchesWithQueryText(s);
        mSearchByNameQuery.postValue(s);
        return true;
    }

    @Override
    public void onFilterItemCloseClick(SearchFilterItem item) {
        onFilterItemRemove(item);
    }

    @Override
    public void onFilterItemRemove(SearchFilterItem item) {
        removeSearchFilterItem(item);
        loadLaunchesWithQueryText(mSearchByNameQuery.getValue());
    }

    @Override
    public void onFilterItemClick(SearchFilterItem item) {
        if (item.getType() == SearchFilterItemType.BY_MISSION_NAME) {
            removeSearchFilterItem(item);
            mSearchByNameQuery.postValue(item.getValue());
            loadLaunchesWithQueryText(item.getValue());
        } else {
            mSearchFilterItemForEdit.postValue(item);
        }
    }

    @Override
    public void onFilterEditFinished(SearchFilterItem oldItem, List<SearchFilterItem> newItems) {
        removeSearchFilterItem(oldItem);
        addNewFilterItemsToCurrentList(newItems);
    }

    private void addNewFilterItemsToCurrentList(List<SearchFilterItem> newItems) {
        if (newItems != null && !newItems.isEmpty()) {
            List<SearchFilterItem> filterList = getCurrentSearchFilter();
            for (SearchFilterItem item : newItems) {
                item.setSelected(false);
                addNewFilterItemToList(filterList, item);
            }
            mSearchFilter.postValue(filterList);
            addNewFilterToList(filterList, mSearchByNameQuery.getValue(), SearchFilterItemType.BY_MISSION_NAME);
            loadLaunches(filterList);
        }
    }

    private void loadLaunchesWithQueryText(String query) {
        List<SearchFilterItem> filterList = new ArrayList<>(getCurrentSearchFilter());
        addNewFilterToList(filterList, query, SearchFilterItemType.BY_MISSION_NAME);
        loadLaunches(filterList);
    }

    private void loadLaunches(List<SearchFilterItem> launchSearchFilter) {
        if (mLaunchLiveDisposable != null) {
            mLaunchLiveDisposable.dispose();
        }
        mLaunchLiveDisposable = mLaunchService.getLaunchesLiveWithFilter(launchSearchFilter)
                .observeOn(Schedulers.io())
                .subscribe(mLaunches::postValue, Timber::d);
    }

    private List<SearchFilterItem> getCurrentSearchFilter() {
        List<SearchFilterItem> filterList;
        filterList = mSearchFilter.getValue();
        if (filterList == null) {
            filterList = new ArrayList<>();
        }
        return filterList;
    }


    private void addNewFilterToList(List<SearchFilterItem> filterList, String value, SearchFilterItemType type) {
        if (value != null && !value.isEmpty()) {
            SearchFilterItem newItem = new SearchFilterItem(value, type);
            addNewFilterItemToList(filterList, newItem);
        }
    }

    private void addNewFilterItemToList(List<SearchFilterItem> filterList, SearchFilterItem newItem) {
        if (newItem != null) {
            boolean isFound = false;
            if (filterList == null) {
                filterList = new ArrayList<>();
            } else {
                for (SearchFilterItem filter : filterList) {
                    if (filter.getType() == newItem.getType() && (filter.getValue().contains(newItem.getValue()))) {
                        isFound = true;
                        break;
                    }
                }
            }
            if (!isFound) {
                filterList.add(newItem);
            }
        }
    }



    private void removeSearchFilterItem(SearchFilterItem item) {
        if (item != null) {
            List<SearchFilterItem> filterList = getCurrentSearchFilter();
            filterList.remove(item);
            mSearchFilter.postValue(filterList);
        }
    }

    public MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public MutableLiveData<Boolean> getIsLoadInProgress() {
        return mIsLoadData;
    }

    public MutableLiveData<List<DomainLaunch>> getLaunches() {
        return mLaunches;
    }

    public MutableLiveData<List<SearchFilterItem>> getSearchFilter() {
        return mSearchFilter;
    }

    public MutableLiveData<String> getSearchByNameQuery() {
        return mSearchByNameQuery;
    }

    @Override
    public MutableLiveData<SearchFilterItem> getSearchFilterItemForEdit() {
        return mSearchFilterItemForEdit;
    }

}
