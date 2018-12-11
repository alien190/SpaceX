package com.mobdev.data.utils;

import com.mobdev.data.common.LaunchForTest;
import com.mobdev.data.model.DataLaunch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DomainToDataConverterTest {
    private LaunchForTest mLaunchForTest;

    @Before
    public void setUp() throws Exception {
        mLaunchForTest = new LaunchForTest();

    }

    @Test
    public void convertLaunchList() {
        List<DataLaunch> dataLaunches = DomainToDataConverter.convertLaunchList(mLaunchForTest.getDomainLaunches());
        assertEquals(2, dataLaunches.size());
        mLaunchForTest.compareLaunchOne(dataLaunches.get(0));
        mLaunchForTest.compareLaunchTwo(dataLaunches.get(1));
    }

    @Test
    public void convertLaunch() {
        DataLaunch dataLaunch = DomainToDataConverter.convertLaunch(mLaunchForTest.getDomainLaunchOne());
        mLaunchForTest.compareLaunchOne(dataLaunch);
    }



    @After
    public void tearDown() throws Exception {
    mLaunchForTest.tearDown();
    }
}