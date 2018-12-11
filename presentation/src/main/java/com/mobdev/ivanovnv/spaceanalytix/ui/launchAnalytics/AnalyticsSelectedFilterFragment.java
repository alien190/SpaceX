package com.mobdev.ivanovnv.spaceanalytix.ui.launchAnalytics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mobdev.ivanovnv.spaceanalytix.R;
import com.mobdev.ivanovnv.spaceanalytix.databinding.AnalyticsSelectedFilterBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import toothpick.Scope;
import toothpick.Toothpick;


public class AnalyticsSelectedFilterFragment extends Fragment {
    private String mScopeName;

    @BindView(R.id.btn_add_filter)
    ImageButton mAddFilterImageButton;

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
        ButterKnife.bind(this, analyticsSelectedFilterBinding.getRoot());
        return analyticsSelectedFilterBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAddFilterImageButton.setOnClickListener(v -> showEditDialogFragment());
    }

    @Override
    public void onPause() {
        super.onPause();
        mAddFilterImageButton.setOnClickListener(null);
    }

    private void showEditDialogFragment() {
        AnalyticsFilterChoiceDialogFragment.showDialogFragment(this, mScopeName);
    }
}

