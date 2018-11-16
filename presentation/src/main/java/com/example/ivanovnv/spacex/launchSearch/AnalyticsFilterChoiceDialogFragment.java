package com.example.ivanovnv.spacex.launchSearch;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.service.ILaunchService;
import com.example.ivanovnv.spacex.databinding.SearchFilterItemBinding;
import com.example.ivanovnv.spacex.di.launchSearch.SearchByRocketNameModule;
import com.example.ivanovnv.spacex.di.launchSearch.SearchByYearModule;

import toothpick.Scope;
import toothpick.Toothpick;

public class AnalyticsFilterChoiceDialogFragment extends BottomSheetDialogFragment {
    private static final String SCOPE_NAME_KEY = "AnalyticsFilterChoiceDialogFragment.ScopeName";
    private static final String SEARCH_BY_ROCKET_NAME_SCOPE_NAME = "SearchByRocketNameScope";
    private static final String SEARCH_BY_YEAR_SCOPE_NAME = "SearchByYearScope";


    public static AnalyticsFilterChoiceDialogFragment newInstance(String scopeName) {
        Bundle args = new Bundle();

        AnalyticsFilterChoiceDialogFragment fragment = new AnalyticsFilterChoiceDialogFragment();
        args.putString(SCOPE_NAME_KEY, scopeName);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            String scopeName = args.getString(SCOPE_NAME_KEY);
            if (scopeName != null && !scopeName.isEmpty()) {
                ViewDataBinding dataBinding = getDataBinding(inflater, scopeName);
                dataBinding.setLifecycleOwner(this);
                dataBinding.executePendingBindings();
                return dataBinding.getRoot();
            }
        }
        throw new IllegalArgumentException("Scope name can't be null or empty");
    }

    private ViewDataBinding getDataBinding(@NonNull LayoutInflater inflater, @NonNull String scopeName) {
        SearchFilterItemBinding searchFilterItemBinding = SearchFilterItemBinding.inflate(inflater);
        openScopes(scopeName);
        searchFilterItemBinding.setSearchByRocketNameScopeName(SEARCH_BY_ROCKET_NAME_SCOPE_NAME);
        searchFilterItemBinding.setSearchByYearScopeName(SEARCH_BY_YEAR_SCOPE_NAME);
        return searchFilterItemBinding;
    }

    public static void showDialogFragment(Fragment fragment, String scopeName) {
        if (fragment != null && scopeName != null && !scopeName.isEmpty()) {
            FragmentManager fragmentManager = fragment.getFragmentManager();
            if (fragmentManager != null) {
                AnalyticsFilterChoiceDialogFragment launchSearchFilterDialogFragment =
                        AnalyticsFilterChoiceDialogFragment.newInstance(scopeName);
                launchSearchFilterDialogFragment.show(fragmentManager, scopeName);
            } else {
                throw new RuntimeException("getFragmentManager() return null");
            }
        } else {
            throw new RuntimeException("fragment can't be null and scopeName can't be empty or null");
        }
    }

    private void openScopes(String parentScopeName) {
        Scope scope;

        scope = Toothpick.openScopes(parentScopeName, SEARCH_BY_ROCKET_NAME_SCOPE_NAME);
        scope.installModules(new SearchByRocketNameModule(scope.getInstance(ILaunchService.class)));

        scope = Toothpick.openScopes(parentScopeName, SEARCH_BY_YEAR_SCOPE_NAME);
        scope.installModules(new SearchByYearModule(scope.getInstance(ILaunchService.class)));
    }

    private void closeScopes() {
        Toothpick.closeScope(SEARCH_BY_ROCKET_NAME_SCOPE_NAME);
        Toothpick.closeScope(SEARCH_BY_YEAR_SCOPE_NAME);
    }

    @Override
    public void onDestroy() {
        closeScopes();
        super.onDestroy();
    }
}
