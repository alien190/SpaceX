package com.example.ivanovnv.spacex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.Analytics.AnalyticsFragment;
import com.example.ivanovnv.spacex.LaunchFragment.LaunchFragment;

public class MainFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;


    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fr_main, container, false);

            viewPager = view.findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = view.findViewById(R.id.tablayout);
            tabLayout.setupWithViewPager(viewPager);
        }

        return view;

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(LaunchFragment.newInstance(), getString(R.string.launches));
        adapter.addFragment(AnalyticsFragment.newInstance(), getString(R.string.analytics));
        viewPager.setAdapter(adapter);
    }
}


