package com.example.ivanovnv.spacex;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ivanovnv.spacex.Analytics.AnalyticsFragment;
import com.example.ivanovnv.spacex.LaunchFragment.LaunchFragment;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LaunchFragment.newInstance(), getString(R.string.launches));
        adapter.addFragment(AnalyticsFragment.newInstance(), getString(R.string.analytics));
        viewPager.setAdapter(adapter);
    }

}