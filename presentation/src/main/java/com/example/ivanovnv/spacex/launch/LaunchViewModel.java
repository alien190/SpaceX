package com.example.ivanovnv.spacex.launch;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v4.widget.SwipeRefreshLayout;

import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.domain.model.searchFilter.LaunchSearchType;
import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.launchSearch.ILaunchSearchViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LaunchViewModel extends ViewModel implements ILaunchListViewModel, ILaunchSearchViewModel {

    private MutableLiveData<Boolean> mIsLoadData = new MutableLiveData<>();
    private MutableLiveData<SwipeRefreshLayout.OnRefreshListener> mOnRefreshListener = new MutableLiveData<>();
    private MutableLiveData<List<DomainLaunch>> mLaunches = new MutableLiveData<>();
    private MutableLiveData<List<LaunchSearchFilter>> mSearchFilter = new MutableLiveData<>();
    private MutableLiveData<String> mSearchByNameQuery = new MutableLiveData<>();
    private MutableLiveData<LaunchSearchFilter> mSearchFilterItemForEdit = new MutableLiveData<>();
    private ILaunchService mLaunchService;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    public LaunchViewModel(ILaunchService launchService) {
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
        List<LaunchSearchFilter> filterList = getCurrentSearchFilter();
        addNewFilterToList(filterList, s, LaunchSearchType.BY_MISSION_NAME);
        mSearchFilter.postValue(filterList);
        mSearchByNameQuery.postValue("");
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        loadLaunchesWithQueryText(s);
        mSearchByNameQuery.postValue(s);
        return true;
    }


    @Override
    public void onFilterItemRemove(LaunchSearchFilter item) {
        removeSearchFilterItem(item);
        loadLaunchesWithQueryText(mSearchByNameQuery.getValue());
    }

    @Override
    public void onFilterItemClick(LaunchSearchFilter item) {
        if (item.getType() == LaunchSearchType.BY_MISSION_NAME) {
            removeSearchFilterItem(item);
            mSearchByNameQuery.postValue(item.getValue());
            loadLaunchesWithQueryText(item.getValue());
        } else {
            mSearchFilterItemForEdit.postValue(item);
        }
    }

    private void loadLaunchesWithQueryText(String query) {
        List<LaunchSearchFilter> filterList = new ArrayList<>(getCurrentSearchFilter());
        addNewFilterToList(filterList, query, LaunchSearchType.BY_MISSION_NAME);
        loadLaunches(filterList);
    }

    private void loadLaunches(List<LaunchSearchFilter> launchSearchFilter) {
        compositeDisposable.add(
                mLaunchService.getLaunchesLiveWithFilter(launchSearchFilter)
                        .observeOn(Schedulers.io())
                        .subscribe(mLaunches::postValue, Timber::d));
    }

    private List<LaunchSearchFilter> getCurrentSearchFilter() {
        List<LaunchSearchFilter> filterList;
        filterList = mSearchFilter.getValue();
        if (filterList == null) {
            filterList = new ArrayList<>();
        }
        return filterList;
    }

    private void addNewFilterToList(List<LaunchSearchFilter> filterList, String value, LaunchSearchType type) {
        if (value != null && !value.isEmpty()) {
            boolean isFound = false;
            if (filterList == null) {
                filterList = new ArrayList<>();
            } else {
                for (LaunchSearchFilter filter : filterList) {
                    if (filter.getType() == type && (filter.getValue().contains(value))) {
                        isFound = true;
                        break;
                    }
                }
            }
            if (!isFound) {
                LaunchSearchFilter filter = new LaunchSearchFilter(value, type);
                if (!filterList.contains(filter)) {
                    filterList.add(filter);
                }
            }
        }
    }

    private void removeSearchFilterItem(LaunchSearchFilter item) {
        List<LaunchSearchFilter> filterList = getCurrentSearchFilter();
        filterList.remove(item);
        mSearchFilter.postValue(filterList);
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

    public MutableLiveData<List<LaunchSearchFilter>> getSearchFilter() {
        return mSearchFilter;
    }

    public MutableLiveData<String> getSearchByNameQuery() {
        return mSearchByNameQuery;
    }

    @Override
    public MutableLiveData<LaunchSearchFilter> getSearchFilterItemForEdit() {
        return mSearchFilterItemForEdit;
    }
}
