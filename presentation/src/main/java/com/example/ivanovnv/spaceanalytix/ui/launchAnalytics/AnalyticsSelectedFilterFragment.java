package com.example.ivanovnv.spaceanalytix.ui.launchAnalytics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ivanovnv.spaceanalytix.R;
import com.example.ivanovnv.spaceanalytix.databinding.AnalyticsSelectedFilterBinding;

import butterknife.ButterKnife;
import toothpick.Scope;
import toothpick.Toothpick;


public class AnalyticsSelectedFilterFragment extends Fragment {
    private String mScopeName;

    public static AnalyticsSelectedFilterFragment newInstance() {
        Bundle args = new Bundle();

        AnalyticsSelectedFilterFragment fragment = new AnalyticsSelectedFilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AnalyticsSelectedFilterBinding analyticsSelectedFilterBinding = AnalyticsSelectedFilterBinding.inflate(inflater);
        ButterKnife.bind(this, analyticsSelectedFilterBinding.getRoot());
        mScopeName = "AnalyticsFragment";
        Scope scope = Toothpick.openScope(mScopeName);
        Toothpick.inject(this, scope);
        analyticsSelectedFilterBinding.setScopeName(mScopeName);
        analyticsSelectedFilterBinding.setLifecycleOwner(this);
        analyticsSelectedFilterBinding.executePendingBindings();
        //temp
        ImageButton imageButton = analyticsSelectedFilterBinding.getRoot().findViewById(R.id.btn_add_filter);
        imageButton.setOnClickListener(v -> showEditDialogFragment());
        //
        return analyticsSelectedFilterBinding.getRoot();
    }

    private void showEditDialogFragment() {
        AnalyticsFilterChoiceDialogFragment.showDialogFragment(this, mScopeName);
    }
}

