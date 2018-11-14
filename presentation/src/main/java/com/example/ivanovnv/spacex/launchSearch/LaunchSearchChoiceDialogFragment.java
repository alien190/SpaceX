package com.example.ivanovnv.spacex.launchSearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.databinding.SearchFilterItemBinding;

public class LaunchSearchChoiceDialogFragment extends BottomSheetDialogFragment {
    private static final String SCOPE_NAME_KEY = "LaunchSearchChoiceDialogFragment.ScopeName";


    public static LaunchSearchChoiceDialogFragment newInstance(String scopeName) {
        Bundle args = new Bundle();

        LaunchSearchChoiceDialogFragment fragment = new LaunchSearchChoiceDialogFragment();
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
                searchFilterItemBinding.setScopeName(scopeName);
                searchFilterItemBinding.setLifecycleOwner(this);
                searchFilterItemBinding.executePendingBindings();
                return searchFilterItemBinding.getRoot();
            }
        }
        throw new IllegalArgumentException("Scope name can't be null or empty");
    }

}
