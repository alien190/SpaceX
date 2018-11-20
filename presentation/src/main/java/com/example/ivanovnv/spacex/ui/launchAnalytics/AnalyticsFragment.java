package com.example.ivanovnv.spacex.ui.launchAnalytics;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.databinding.AnalyticsChartBinding;

import toothpick.Scope;
import toothpick.Toothpick;

public class AnalyticsFragment extends Fragment {
    public static AnalyticsFragment newInstance() {
        Bundle args = new Bundle();

        AnalyticsFragment fragment = new AnalyticsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Scope scope = Toothpick.openScope("AnalyticsFragment");
        AnalyticsChartBinding analyticsChartBinding = AnalyticsChartBinding.inflate(inflater);
        analyticsChartBinding.setVm(scope.getInstance(ILaunchAnalyticsViewModel.class));
        analyticsChartBinding.setLifecycleOwner(this);
        return analyticsChartBinding.getRoot();
    }
}
