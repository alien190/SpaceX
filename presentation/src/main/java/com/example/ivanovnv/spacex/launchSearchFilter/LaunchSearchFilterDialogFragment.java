package com.example.ivanovnv.spacex.launchSearchFilter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.domain.model.searchFilter.LaunchSearchFilter;
import com.example.ivanovnv.spacex.databinding.SearchFilterItemBinding;

import javax.inject.Inject;

import toothpick.Scope;
import toothpick.Toothpick;

public class LaunchSearchFilterDialogFragment extends BottomSheetDialogFragment {
    private static final String SCOPE_NAME_KEY = "LaunchSearchFilterDialogFragment.ScopeName";

    @Inject
    ILaunchSearchFilterViewModel mViewModel;

    public static LaunchSearchFilterDialogFragment newInstance(String scopeName) {
        Bundle args = new Bundle();

        LaunchSearchFilterDialogFragment fragment = new LaunchSearchFilterDialogFragment();
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
                SearchFilterItemBinding searchFilterItemBinding = SearchFilterItemBinding.inflate(inflater);
                Scope scope = Toothpick.openScope(scopeName);
                initViewModel(scope);
                searchFilterItemBinding.setScopeName(scopeName);
                searchFilterItemBinding.setVm(mViewModel);
                searchFilterItemBinding.setLifecycleOwner(this);
                searchFilterItemBinding.executePendingBindings();
                return searchFilterItemBinding.getRoot();
            }
        }
        throw new IllegalArgumentException("Scope name can't be null or empty");
    }

    private void initViewModel(Scope scope) {
        Toothpick.inject(this, scope);
        mViewModel.setLaunchSearchFilterForEdit(scope.getInstance(LaunchSearchFilter.class));
        mViewModel.initListRocketNames();
        mViewModel.getCloseDialog().observe(this, this::onClose);
    }

    private void onClose(Boolean canClose) {
        if (canClose != null && canClose) {
            mViewModel.getCloseDialog().postValue(false);
            dismiss();
        }
    }
}
