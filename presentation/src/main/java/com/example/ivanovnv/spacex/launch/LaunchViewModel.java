package com.example.ivanovnv.spacex.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.searchFilter.ISearchFilter;
import com.example.domain.model.searchFilter.SearchFilterItem;
import com.example.domain.model.searchFilter.SearchFilterItemType;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.currentPreferences.ICurrentPreferences;
import com.example.ivanovnv.spacex.launchSearch.ILaunchSearchViewModel;
import com.example.ivanovnv.spacex.launchSearchFilter.ILaunchSearchFilterCallback;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchViewModel extends ViewModel
        implements ILaunchListViewModel, ILaunchSearchViewModel, ILaunchSearchFilterCallback {

    private MutableLiveData<Boolean> mIsLoadData = new MutableLiveData<>();
    private MutableLiveData<SwipeRefreshLayout.OnRefreshListener> mOnRefreshListener = new MutableLiveData<>();
    private MutableLiveData<List<DomainLaunch>> mLaunches = new MutableLiveData<>();
    private MutableLiveData<List<SearchFilterItem>> mSearchFilterLive = new MutableLiveData<>();
    private MutableLiveData<String> mSearchByNameQuery = new MutableLiveData<>();
    private MutableLiveData<SearchFilterItem> mSearchFilterItemForEdit = new MutableLiveData<>();
    private ILaunchService mLaunchService;
    private ICurrentPreferences mCurrentPreferences;
    private ISearchFilter mSearchFilter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Disposable mLaunchLiveDisposable;


    public LaunchViewModel(ILaunchService launchService, ICurrentPreferences currentPreferences) {
        mLaunchService = launchService;
        mCurrentPreferences = currentPreferences;
        mSearchFilter = currentPreferences.getSearchFilter();
        compositeDisposable.add(mSearchFilter.getSearchFilterLive()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadLaunches, Timber::d));
        mOnRefreshListener.postValue(this::refreshLaunches);
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
        //submitTextSearch(s);
        return true;
    }

    public void submitTextSearch() {
        submitTextSearch(mSearchByNameQuery.getValue());
    }

    public void submitTextSearch(String s) {
//        List<SearchFilterItem> filterList = getCurrentSearchFilter();
//        addNewFilterToList(filterList, s, SearchFilterItemType.BY_MISSION_NAME);
//        mSearchFilterLive.postValue(filterList);
//        mSearchByNameQuery.setValue("");
    }

    @Override
    public boolean onQueryTextChange(String s) {
//        loadLaunchesWithQueryText(s);
//        mSearchByNameQuery.postValue(s);
        return true;
    }

//    @Override
//    public void onFilterItemCloseClick(SearchFilterItem item) {
//        onFilterItemRemove(item);
//    }

    @Override
    public void onFilterItemRemove(SearchFilterItem item) {
//        mSearchFilter.deleteItem(item);
//        //removeSearchFilterItem(item);
//        loadLaunchesWithQueryText(mSearchByNameQuery.getValue());
    }

    @Override
    public void onFilterItemClick(int index) {
//        if (item.getType() == SearchFilterItemType.BY_MISSION_NAME) {
//            mSearchFilter.deleteItem(item);
//            //removeSearchFilterItem(item);
//            mSearchByNameQuery.postValue(item.getValue());
//            loadLaunchesWithQueryText(item.getValue());
//        } else {
//            mSearchFilterItemForEdit.postValue(item);
//        }
    }

    @Override
    public void onFilterEditFinished(SearchFilterItem oldItem, List<SearchFilterItem> newItems) {
//        mSearchFilter.deleteItem(oldItem);
//        //removeSearchFilterItem(oldItem);
//        addNewFilterItemsToCurrentList(newItems);
    }

//    private void addNewFilterItemsToCurrentList(List<SearchFilterItem> newItems) {
//        if (newItems != null && !newItems.isEmpty()) {
//            List<SearchFilterItem> filterList = getCurrentSearchFilter();
//            for (SearchFilterItem item : newItems) {
//                item.setSelected(false);
//                addNewFilterItemToList(filterList, item);
//            }
//            mSearchFilterLive.postValue(filterList);
//            addNewFilterToList(filterList, mSearchByNameQuery.getValue(), SearchFilterItemType.BY_MISSION_NAME);
//            loadLaunches(filterList);
//        }
//}

    private void loadLaunchesWithQueryText(String query) {
//        List<SearchFilterItem> filterList = new ArrayList<>(getCurrentSearchFilter());
//        addNewFilterToList(filterList, query, SearchFilterItemType.BY_MISSION_NAME);
//        loadLaunches(filterList);
    }

    private void loadLaunches(ISearchFilter searchFilter) {
        if (mLaunchLiveDisposable != null) {
            mLaunchLiveDisposable.dispose();
        }
        mLaunchLiveDisposable = mLaunchService.getLaunchesLiveWithFilter(searchFilter)
                .observeOn(Schedulers.io())
                .subscribe(mLaunches::postValue, Timber::d);
    }

//    private List<SearchFilterItem> getCurrentSearchFilter() {
//        List<SearchFilterItem> filterList;
//        filterList = mSearchFilterLive.getValue();
//        if (filterList == null) {
//            filterList = new ArrayList<>();
//        }
//        return filterList;
//    }
//
//
//    private void addNewFilterToList(List<SearchFilterItem> filterList, String value, SearchFilterItemType type) {
//        if (value != null && !value.isEmpty()) {
//            SearchFilterItem newItem = new SearchFilterItem(value, type);
//            addNewFilterItemToList(filterList, newItem);
//        }
//    }
//
//    private void addNewFilterItemToList(List<SearchFilterItem> filterList, SearchFilterItem newItem) {
//        if (newItem != null) {
//            boolean isFound = false;
//            if (filterList == null) {
//                filterList = new ArrayList<>();
//            } else {
//                for (SearchFilterItem filter : filterList) {
//                    if (filter.getType() == newItem.getType() && (filter.getValue().contains(newItem.getValue()))) {
//                        isFound = true;
//                        break;
//                    }
//                }
//            }
//            if (!isFound) {
//                filterList.add(newItem);
//            }
//        }
//    }


//    private void removeSearchFilterItem(SearchFilterItem item) {
//        if (item != null) {
//            List<SearchFilterItem> filterList = getCurrentSearchFilter();
//            filterList.remove(item);
//            mSearchFilterLive.postValue(filterList);
//        }
//    }

    public MutableLiveData<SwipeRefreshLayout.OnRefreshListener> getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public MutableLiveData<Boolean> getIsLoadInProgress() {
        return mIsLoadData;
    }

    public MutableLiveData<List<DomainLaunch>> getLaunches() {
        return mLaunches;
    }

    public MutableLiveData<List<SearchFilterItem>> getSearchFilterLive() {
        return mSearchFilterLive;
    }

    public MutableLiveData<String> getSearchByNameQuery() {
        return mSearchByNameQuery;
    }

    @Override
    public MutableLiveData<SearchFilterItem> getSearchFilterItemForEdit() {
        return mSearchFilterItemForEdit;
    }

}
