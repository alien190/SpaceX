package com.mobdev.ivanovnv.spaceanalytix.ui.launchAnalytics;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobdev.ivanovnv.spaceanalytix.databinding.AnalyticsChartBinding;

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
