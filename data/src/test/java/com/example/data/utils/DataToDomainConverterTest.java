package com.example.data.utils;

import com.example.data.common.LaunchForTest;
import com.example.data.model.DataAnalytics;
import com.example.data.model.DataLaunch;
import com.example.data.utils.DataToDomainConverter;
import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.analytics.DomainAnalyticsItem;
import com.example.domain.model.filter.AnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilterItem;
import com.example.domain.model.launch.DomainLaunch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataToDomainConverterTest {


    private LaunchForTest mLaunchForTest;


    @Before
    public void setUp() throws Exception {
        mLaunchForTest = new LaunchForTest();
    }


    @Test
    public void convertLaunch() {
        DomainLaunch domainLaunch = DataToDomainConverter.convertLaunch(mLaunchForTest.getDataLaunchOne());
        mLaunchForTest.compareLaunchOne(domainLaunch);
    }

    @Test
    public void convertLaunchList() {
        List<DomainLaunch> domainLaunches = DataToDomainConverter.convertLaunchList(mLaunchForTest.getDataLaunches());
        assertEquals(2, domainLaunches.size());
        mLaunchForTest.compareLaunchOne(domainLaunches.get(0));
        mLaunchForTest.compareLaunchTwo(domainLaunches.get(1));
    }

    @Test
    public void convertAnalytics() {
        DomainAnalytics domainAnalytics = DataToDomainConverter.convertAnalytics(
                mLaunchForTest.getDataAnalytics(), mLaunchForTest.getAnalyticsFilter());
        mLaunchForTest.compareAnalytics(domainAnalytics);
    }


    @After
    public void tearDown() throws Exception {
        mLaunchForTest.tearDown();

    }

}