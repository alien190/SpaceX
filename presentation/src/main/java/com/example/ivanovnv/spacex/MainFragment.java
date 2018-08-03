package com.example.ivanovnv.spacex;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivanovnv.spacex.Analytics.AnalyticsFragment;
import com.example.ivanovnv.spacex.LaunchFragment.LaunchFragment;

public class MainFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private static final String TAG = MainFragment.class.getSimpleName();
    private ViewPagerAdapter mViewPagerAdapter;

    public static MainFragment newInstance() {

        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        //fragment.setRetainInstance(true);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        if (view == null) {
            view = inflater.inflate(R.layout.fr_main, container, false);

            viewPager = view.findViewById(R.id.viewpager);
            setupViewPager(viewPager);

            tabLayout = view.findViewById(R.id.tablayout);
            tabLayout.setupWithViewPager(viewPager);
        }

        return view;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

    }

    private void setupViewPager(ViewPager viewPager) {

        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

//        Fragment launchFragment = null;
//
//        List<Fragment> fragments = fragmentManager.getFragments();
//        for (Fragment fragment : fragments) {
//            if (fragment instanceof LaunchFragment) launchFragment = fragment;
//        }
//
//        if (launchFragment == null) launchFragment = LaunchFragment.newInstance();

        mViewPagerAdapter.addFragment(LaunchFragment.newInstance(), getString(R.string.launches));
        mViewPagerAdapter.addFragment(AnalyticsFragment.newInstance(), getString(R.string.analytics));
        viewPager.setAdapter(mViewPagerAdapter);
        //mViewPagerAdapter.notifyDataSetChanged();
    }
}


