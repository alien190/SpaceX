package com.mobdev.ivanovnv.spaceanalytix.ui.launchSearch;

import androidx.databinding.ViewDataBinding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mobdev.ivanovnv.spaceanalytix.di.launchSearch.SearchByCountryModule;
import com.mobdev.ivanovnv.spaceanalytix.di.launchSearch.SearchByOrbitModule;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdev.domain.service.ILaunchService;
import com.mobdev.ivanovnv.spaceanalytix.databinding.SearchFilterBinding;
import com.mobdev.ivanovnv.spaceanalytix.di.launchSearch.SearchByRocketNameModule;
import com.mobdev.ivanovnv.spaceanalytix.di.launchSearch.SearchByYearModule;

import toothpick.Scope;
import toothpick.Toothpick;

public class SearchFilterChoiceDialogFragment extends BottomSheetDialogFragment {
    private static final String SCOPE_NAME_KEY = "SearchFilterChoiceDialogFragment.ScopeName";
    private static final String SEARCH_BY_ROCKET_NAME_SCOPE_NAME = "SearchByRocketNameScope";
    private static final String SEARCH_BY_YEAR_SCOPE_NAME = "SearchByYearScope";
    private static final String SEARCH_BY_COUNTRY_SCOPE_NAME = "SearchByCountry";
    private static final String SEARCH_BY_ORBIT_SCOPE_NAME = "SearchByOrbit";


    public static SearchFilterChoiceDialogFragment newInstance(String scopeName) {
        Bundle args = new Bundle();

        SearchFilterChoiceDialogFragment fragment = new SearchFilterChoiceDialogFragment();
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
        SearchFilterBinding searchFilterBinding = SearchFilterBinding.inflate(inflater);
        openScopes(scopeName);
        searchFilterBinding.setSearchByRocketNameScopeName(SEARCH_BY_ROCKET_NAME_SCOPE_NAME);
        searchFilterBinding.setSearchByYearScopeName(SEARCH_BY_YEAR_SCOPE_NAME);
        searchFilterBinding.setSearchByCountryScopeName(SEARCH_BY_COUNTRY_SCOPE_NAME);
        searchFilterBinding.setSearchByOrbitScopeName(SEARCH_BY_ORBIT_SCOPE_NAME);
        return searchFilterBinding;
    }

    public static void showDialogFragment(Fragment fragment, String scopeName) {
        if (fragment != null && scopeName != null && !scopeName.isEmpty()) {
            FragmentManager fragmentManager = fragment.getFragmentManager();
            if (fragmentManager != null) {
                SearchFilterChoiceDialogFragment launchSearchFilterDialogFragment =
                        SearchFilterChoiceDialogFragment.newInstance(scopeName);
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
        ILaunchService launchService = scope.getInstance(ILaunchService.class);
        scope.installModules(new SearchByRocketNameModule(launchService));

        scope = Toothpick.openScopes(parentScopeName, SEARCH_BY_YEAR_SCOPE_NAME);
        scope.installModules(new SearchByYearModule(launchService));

        scope = Toothpick.openScopes(parentScopeName, SEARCH_BY_COUNTRY_SCOPE_NAME);
        scope.installModules(new SearchByCountryModule(launchService));

        scope = Toothpick.openScopes(parentScopeName, SEARCH_BY_ORBIT_SCOPE_NAME);
        scope.installModules(new SearchByOrbitModule(launchService));
    }

    private void closeScopes() {
        Toothpick.closeScope(SEARCH_BY_ROCKET_NAME_SCOPE_NAME);
        Toothpick.closeScope(SEARCH_BY_YEAR_SCOPE_NAME);
        Toothpick.closeScope(SEARCH_BY_COUNTRY_SCOPE_NAME);
        Toothpick.closeScope(SEARCH_BY_ORBIT_SCOPE_NAME);
    }

    @Override
    public void onDestroy() {
        closeScopes();
        super.onDestroy();
    }
}
