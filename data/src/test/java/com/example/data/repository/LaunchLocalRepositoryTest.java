package com.example.data.repository;

import android.content.Context;

import com.example.data.R;
import com.example.data.common.LaunchForTest;
import com.example.data.database.LaunchDao;
import com.example.data.model.DataLaunch;
import com.example.domain.model.analytics.DomainAnalytics;
import com.example.domain.model.filter.AnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilter;
import com.example.domain.model.filter.IAnalyticsFilterItem;
import com.example.domain.model.filter.ISearchFilter;
import com.example.domain.model.filter.SearchFilter;
import com.example.domain.model.launch.DomainLaunch;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LaunchLocalRepositoryTest {

    private final String mSearchByMissionTitle = "по миссиям";
    private final String mSearchByCountryTitle = "по странам";
    private final String mSearchByRocketTitle = "по типам ракет";
    private final String mSearchByYearTitle = "по годам";
    private final String mSearchByOrbitTitle = "по орбитам";
    private ISearchFilter mSearchFilter;
    private LaunchLocalRepository mLaunchLocalRepository;

    @Mock
    private Context mMockContext;

    @Mock
    private LaunchDao mMockLaunchDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private LaunchForTest mLaunchForTest;

    @Before
    public void setUp() throws Exception {
        mLaunchForTest = new LaunchForTest();
        initMockContext();
        initMockLaunchDao();
        mLaunchLocalRepository = new LaunchLocalRepository(mMockLaunchDao, mMockContext);
        mSearchFilter = new SearchFilter();
    }

    private void initMockLaunchDao() {
        when(mMockLaunchDao.getLaunches()).thenReturn(mLaunchForTest.getDataLaunches());
        when(mMockLaunchDao.getLaunchesLive()).thenReturn(Flowable.just(mLaunchForTest.getDataLaunches()));

        DataLaunch dataLaunchOne = mLaunchForTest.getDataLaunchOne();
        when(mMockLaunchDao.getLaunchByFlightNumber(anyInt())).thenAnswer(
                (Answer<Single>) invocation -> {
                    Object[] args = invocation.getArguments();
                    dataLaunchOne.setFlight_number((Integer) args[0]);
                    return Single.just(dataLaunchOne);
                });

        when(mMockLaunchDao.getImageId(anyInt(), anyString())).thenAnswer((Answer<Integer>) invocation -> {
            Object[] args = invocation.getArguments();
            int flightNumber = (int) args[0];
            String missionPathSmall = (String) args[1];

            for (DataLaunch launch : mLaunchForTest.getDataLaunches()) {
                if (flightNumber == launch.getFlight_number() &&
                        missionPathSmall.equals(launch.getMission_patch_small())) {
                    return launch.getImageId();
                }
            }
            return 0;
        });

        when(mMockLaunchDao.getLaunchesLiveWithFilter(any())).thenAnswer(
                (Answer<Flowable>) invocation -> Flowable.just(mLaunchForTest.getDataLaunches()));

        when(mMockLaunchDao.getAnalyticsLive(any())).thenAnswer(
                (Answer<Flowable>) invocation -> Flowable.just(mLaunchForTest.getDataAnalytics()));
    }

    @Test
    public void generateSqlWhereConditions() {
        assertEquals("", mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));

        mSearchFilter.addItem("tess", ISearchFilter.ItemType.BY_MISSION_NAME);
        assertEquals("(mission_name LIKE '%tess%')",
                mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));

        mSearchFilter.getItem(0).setSelected(false);
        assertEquals("",
                mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));

        mSearchFilter.addItem("tess", ISearchFilter.ItemType.BY_MISSION_NAME);
        mSearchFilter.addItem("rocket1", ISearchFilter.ItemType.BY_ROCKET_NAME);
        mSearchFilter.addItem("rocket2", ISearchFilter.ItemType.BY_ROCKET_NAME);
        assertEquals("(mission_name LIKE '%tess%')",
                mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));

        mSearchFilter.getItem(1).setSelected(true);
        mSearchFilter.getItem(2).setSelected(true);
        assertEquals("(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' " +
                        "OR rocket_name LIKE '%rocket2%')",
                mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));

        mSearchFilter.addItem("2008", ISearchFilter.ItemType.BY_LAUNCH_YEAR);
        mSearchFilter.addItem("2012", ISearchFilter.ItemType.BY_LAUNCH_YEAR);
        assertEquals("(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' " +
                        "OR rocket_name LIKE '%rocket2%')",
                mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));

        mSearchFilter.getItem(3).setSelected(true);
        assertEquals("(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' " +
                        "OR rocket_name LIKE '%rocket2%') AND (launch_year LIKE '%2008%')",
                mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));

        mSearchFilter.getItem(4).setSelected(true);
        assertEquals("(mission_name LIKE '%tess%') AND (rocket_name LIKE '%rocket1%' OR " +
                        "rocket_name LIKE '%rocket2%') AND (launch_year LIKE '%2008%' " +
                        "OR launch_year LIKE '%2012%')",
                mLaunchLocalRepository.generateSqlWhereConditions(mSearchFilter));
    }

    private void initMockContext() {
        when(mMockContext.getString(R.string.search_by_year_title)).thenReturn(mSearchByYearTitle);
        when(mMockContext.getString(R.string.search_by_orbit_title)).thenReturn(mSearchByOrbitTitle);
        when(mMockContext.getString(R.string.search_by_mission_title)).thenReturn(mSearchByMissionTitle);
        when(mMockContext.getString(R.string.search_by_country_title)).thenReturn(mSearchByCountryTitle);
        when(mMockContext.getString(R.string.search_by_rocket_title)).thenReturn(mSearchByRocketTitle);
    }

    @After
    public void tearDown() throws Exception {
        mSearchFilter = null;
        mLaunchLocalRepository = null;
    }


    @Test
    public void getAnalyticsFilter() {
        IAnalyticsFilter analyticsFilter = mLaunchLocalRepository.getAnalyticsFilter().blockingGet();
        assertEquals(10, analyticsFilter.getItemsCount());

        IAnalyticsFilter analyticsFilterLaunchCount = analyticsFilter.getFilterByType(IAnalyticsFilter.ItemType.LAUNCH_COUNT);
        assertEquals(5, analyticsFilterLaunchCount.getItemsCount());
        testAnalyticsFilterItems(analyticsFilterLaunchCount);

        IAnalyticsFilter analyticsFilterPayloadWeight = analyticsFilter.getFilterByType(IAnalyticsFilter.ItemType.PAYLOAD_WEIGHT);
        assertEquals(5, analyticsFilterPayloadWeight.getItemsCount());
        testAnalyticsFilterItems(analyticsFilterPayloadWeight);
    }

    private void testAnalyticsFilterItems(IAnalyticsFilter analyticsFilterLaunchCount) {
        for (IAnalyticsFilterItem item : analyticsFilterLaunchCount.getItems()) {
            switch (item.getBaseType()) {
                case YEARS: {
                    assertEquals(mSearchByYearTitle, item.getValue());
                    break;
                }
                case ORBITS: {
                    assertEquals(mSearchByOrbitTitle, item.getValue());
                    break;
                }
                case MISSIONS: {
                    assertEquals(mSearchByMissionTitle, item.getValue());
                    break;
                }
                case COUNTRIES: {
                    assertEquals(mSearchByCountryTitle, item.getValue());
                    break;
                }
                case ROCKET: {
                    assertEquals(mSearchByRocketTitle, item.getValue());
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }

    @Test
    public void getPressKitPdf() {
        thrown.expect(Throwable.class);
        thrown.expectMessage("do nothing");
        mLaunchLocalRepository.getPressKitPdf(null).blockingGet();
    }

    @Test
    public void loadImage() {
        assertNull(mLaunchLocalRepository.loadImage(""));
    }

    @Test
    public void loadImageWithResize() throws Exception {
        assertNull(mLaunchLocalRepository.loadImageWithResize("", 0, 0));
    }

    @Test
    public void getLaunches() {
        List<DomainLaunch> domainLaunches = mLaunchLocalRepository.getLaunches().blockingGet();
        mLaunchForTest.compareDomainLaunches(domainLaunches);
    }

    @Test
    public void getLaunchesLive() {
        List<DomainLaunch> domainLaunches = mLaunchLocalRepository.getLaunchesLive().blockingFirst();
        mLaunchForTest.compareDomainLaunches(domainLaunches);
    }

    @Test
    public void getLaunchByFlightNumber() {
        int flightNumber = 5;
        DomainLaunch domainLaunch = mLaunchLocalRepository.getLaunchByFlightNumber(flightNumber).blockingGet();
        assertEquals(flightNumber, domainLaunch.getFlight_number());
    }

    @Test
    public void getImageId() {
        DomainLaunch domainLaunchOne = mLaunchForTest.getDomainLaunchOne();
        assertEquals(domainLaunchOne.getImageId(), mLaunchLocalRepository.getImageId(domainLaunchOne));
    }

    @Test
    public void deleteUnusedImages() {
        assertEquals(true, mLaunchLocalRepository.deleteUnusedImages());
    }

    @Test
    public void getLaunchesLiveWithFilter() {
        List<DomainLaunch> domainLaunches = mLaunchLocalRepository.getLaunchesLiveWithFilter(new SearchFilter()).blockingFirst();
        mLaunchForTest.compareDomainLaunches(domainLaunches);
    }

    @Test
    public void getAnalyticsLiveNullArguments() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("searchFilter and analyticsFilter can't be null");
        mLaunchLocalRepository.getAnalyticsLive(null, null);

        ISearchFilter searchFilter = new SearchFilter();
        IAnalyticsFilter analyticsFilter = new AnalyticsFilter();
        mLaunchLocalRepository.getAnalyticsLive(searchFilter, null);
        mLaunchLocalRepository.getAnalyticsLive(null, analyticsFilter);

        mLaunchLocalRepository.getAnalyticsLive(searchFilter, analyticsFilter);
    }

    @Test
    public void getAnalyticsLiveNullAnalyticsFilter() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("searchFilter and analyticsFilter can't be null");
        ISearchFilter searchFilter = new SearchFilter();
        mLaunchLocalRepository.getAnalyticsLive(searchFilter, null);
    }

    @Test
    public void getAnalyticsLiveNullSearchFilter() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("searchFilter and analyticsFilter can't be null");
        IAnalyticsFilter analyticsFilter = new AnalyticsFilter();
        mLaunchLocalRepository.getAnalyticsLive(null, analyticsFilter);
    }

    @Test
    public void getAnalyticsLiveEmptyAnalyticsFilter() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("filterSelected.getItemsCount() != 1");
        IAnalyticsFilter analyticsFilter = new AnalyticsFilter();
        ISearchFilter searchFilter = new SearchFilter();
        mLaunchLocalRepository.getAnalyticsLive(searchFilter, analyticsFilter);
    }

    @Test
    public void getAnalyticsLive() {
        IAnalyticsFilter analyticsFilter = new AnalyticsFilter();
        ISearchFilter searchFilter = new SearchFilter();
        analyticsFilter.addItem("value", IAnalyticsFilter.ItemType.LAUNCH_COUNT, IAnalyticsFilterItem.BaseType.ROCKET);
        analyticsFilter.getItems().get(0).setSelectedWithoutNotification(true);
        DomainAnalytics domainAnalytics = mLaunchLocalRepository.getAnalyticsLive(searchFilter, analyticsFilter).blockingFirst();
        mLaunchForTest.compareAnalytics(domainAnalytics);
    }

    @Test
    public void insertLaunches() {
    }

    @Test
    public void insertLaunch() {
    }

    @Test
    public void insertImage() {
    }

    @Test
    public void generateSqlFilterQuery() {
    }

    @Test
    public void getSearchFilterLive() {
    }

    @Test
    public void cancelLoadImages() {

    }
}