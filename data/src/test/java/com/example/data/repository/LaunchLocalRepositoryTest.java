package com.example.data.repository;

import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.model.filter.SearchFilter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LaunchLocalRepositoryTest {

    private ISearchFilter mSearchFilter;
    private LaunchLocalRepository mLaunchLocalRepository;

    @Before
    public void setUp() throws Exception {
        mLaunchLocalRepository = new LaunchLocalRepository(null, null);
        mSearchFilter = new SearchFilter();
    }

    @Test
    public void generateSqlWhereConditions() {
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),"");
        mSearchFilter.addItem("tess", ISearchFilter.ItemType.BY_MISSION_NAME);
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),"");
        mSearchFilter.getItem(0).setSelected(true);
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),
                "(mission_name LIKE '%tess%')");
        mSearchFilter.addItem("rocket1", ISearchFilter.ItemType.BY_ROCKET_NAME);
        mSearchFilter.addItem("rocket2", ISearchFilter.ItemType.BY_ROCKET_NAME);
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),
                "(mission_name LIKE '%tess%')");
        mSearchFilter.getItem(1).setSelected(true);
        mSearchFilter.getItem(2).setSelected(true);
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),
                "(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' OR rocket_name LIKE '%rocket2%')");
        mSearchFilter.addItem("2008", ISearchFilter.ItemType.BY_LAUNCH_YEAR);
        mSearchFilter.addItem("2012", ISearchFilter.ItemType.BY_LAUNCH_YEAR);
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),
                "(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' OR rocket_name LIKE '%rocket2%')");
        mSearchFilter.getItem(3).setSelected(true);
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),
                "(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' OR rocket_name LIKE '%rocket2%') AND (launch_year LIKE '%2008%')");
        mSearchFilter.getItem(4).setSelected(true);
        assertEquals(mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter),
                "(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' OR rocket_name LIKE '%rocket2%') AND (launch_year LIKE '%2008%' OR launch_year LIKE '%2012%')");
    }

    @After
    public void tearDown() throws Exception {
        mSearchFilter = null;
        mLaunchLocalRepository = null;
    }
}