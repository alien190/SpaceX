package com.example.ivanovnv.spacex.ui.launchAnalytics;

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
import com.example.ivanovnv.spacex.databinding.AnalyticsFilterBinding;
import com.example.ivanovnv.spacex.di.launchAnalytics.LaunchCountAnalyticsModule;
import com.example.ivanovnv.spacex.di.launchAnalytics.PayloadWeightAnalyticsModule;

import toothpick.Scope;
import toothpick.Toothpick;

public class AnalyticsFilterChoiceDialogFragment extends BottomSheetDialogFragment {
    private static final String SCOPE_NAME_KEY = "AnalyticsFilterChoiceDialogFragment.ScopeName";
    private static final String LAUNCH_COUNT_SCOPE_NAME = "LaunchCountScopeName";
    private static final String PAYLOAD_WEIGHT_SCOPE_NAME = "PayloadWeightScopeName";


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
        AnalyticsFilterBinding analyticsFilterBinding = AnalyticsFilterBinding.inflate(inflater);
        openScopes(scopeName);
        analyticsFilterBinding.setLaunchCountScopeName(LAUNCH_COUNT_SCOPE_NAME);
        analyticsFilterBinding.setPayloadWeightScopeName(PAYLOAD_WEIGHT_SCOPE_NAME);
        return analyticsFilterBinding;
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

        scope = Toothpick.openScopes(parentScopeName, LAUNCH_COUNT_SCOPE_NAME);
        scope.installModules(new LaunchCountAnalyticsModule(scope.getInstance(ILaunchService.class)));

        scope = Toothpick.openScopes(parentScopeName, PAYLOAD_WEIGHT_SCOPE_NAME);
        scope.installModules(new PayloadWeightAnalyticsModule(scope.getInstance(ILaunchService.class)));
    }

    private void closeScopes() {
        Toothpick.closeScope(LAUNCH_COUNT_SCOPE_NAME);
        Toothpick.closeScope(PAYLOAD_WEIGHT_SCOPE_NAME);
    }

    @Override
    public void onDestroy() {
        closeScopes();
        super.onDestroy();
    }
}
