package com.example.ivanovnv.spaceanalytix;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spaceanalytix.ui.launchAnalytics.AnalyticsFragment;
import com.example.ivanovnv.spaceanalytix.ui.launch.LaunchFragment;
import com.example.ivanovnv.spaceanalytix.ui.prefs.MainPreferenceFragment;

public class MainFragment extends Fragment {

    private BottomNavigationView mBottomNavigationView;

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
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBottomNavigationView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    @Override
    public void onStop() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
        super.onStop();
    }

    private boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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


