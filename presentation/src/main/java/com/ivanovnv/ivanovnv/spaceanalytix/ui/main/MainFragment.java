package com.ivanovnv.ivanovnv.spaceanalytix.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ivanovnv.ivanovnv.spaceanalytix.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ivanovnv.ivanovnv.spaceanalytix.ui.launchAnalytics.AnalyticsFragment;
import com.ivanovnv.ivanovnv.spaceanalytix.ui.launch.LaunchFragment;
import com.ivanovnv.ivanovnv.spaceanalytix.ui.prefs.MainPreferenceFragment;

public class MainFragment extends Fragment {

    private static final String SELECTED_ITEM_KEY = "MainFragment.SelectedItemKey";
    private BottomNavigationView mBottomNavigationView;
    private int mSelectedItemId;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_main, container, false);
        mBottomNavigationView = view.findViewById(R.id.bottom_navigation);
        if (savedInstanceState == null) {
            replaceFragment(LaunchFragment.newInstance());
            mSelectedItemId = R.id.mi_launches;
        } else {
            try {
                int id = savedInstanceState.getInt(SELECTED_ITEM_KEY);
                showSelectedFragment(id);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_KEY, mSelectedItemId);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return showSelectedFragment(menuItem.getItemId());
    }

    private boolean showSelectedFragment(int id) {
        mSelectedItemId = id;
        switch (id) {
            case R.id.mi_launches: {
                if (replaceFragment(LaunchFragment.newInstance())) {
                    setExitTransition(TransitionInflater.from(getContext()).inflateTransition(R.transition.launch_list_exit_transition));
                    return true;
                }
                return false;
            }
            case R.id.mi_analytics: {
                return replaceFragment(AnalyticsFragment.newInstance());
            }
            case R.id.mi_settings: {
                return replaceFragment(MainPreferenceFragment.newInstance());
            }
        }
        return false;
    }

    private boolean replaceFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
                return true;
            } else {
                throw new IllegalArgumentException("fragmentManager can't be null");
            }
        } else {
            throw new IllegalArgumentException("fragment can't be null");
        }
    }
}


