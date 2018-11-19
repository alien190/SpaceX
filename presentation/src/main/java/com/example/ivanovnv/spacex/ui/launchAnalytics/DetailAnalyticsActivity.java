package com.example.ivanovnv.spacex.ui.launchAnalytics;

import android.content.Intent;
import android.support.v4.app.Fragment;


import com.example.ivanovnv.spacex.SingleFragmentActivity;

public class DetailAnalyticsActivity extends SingleFragmentActivity {

    public static final String YEAR_KEY = "com.example.ivanovnv.spacex.Analytics.YEAR_KEY";

    @Override
    protected Fragment getFragment() {
        Intent intent = getIntent();
        Fragment fragment = DetailAnalyticsFragment.newInstance(intent.getExtras().getFloat(YEAR_KEY));
        return fragment;
    }

    @Override
    protected void openScope() {

    }

    @Override
    protected void closeScope() {

    }
}
