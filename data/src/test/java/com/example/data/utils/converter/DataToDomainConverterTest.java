package com.example.data.utils.converter;

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

    private DataLaunch mDataLaunchOne;
    private DataLaunch mDataLaunchTwo;
    private List<DataLaunch> mDataLaunches = new ArrayList<>();
    private byte[] mImageOne;
    private byte[] mImageTwo;
    private IAnalyticsFilter mAnalyticsFilter;
    private List<DataAnalytics> mDataAnalytics;


    @Before
    public void setUp() throws Exception {
        mImageOne = new byte[1];
        mImageTwo = new byte[1];

        initDataLaunchOne();
        initDataLaunchTwo();
        mDataLaunches.add(mDataLaunchOne);
        mDataLaunches.add(mDataLaunchTwo);

        initAnalytics();
    }

    private void initAnalytics() {
        mAnalyticsFilter = new AnalyticsFilter();
        mAnalyticsFilter.addItem("filter value",
                IAnalyticsFilter.ItemType.LAUNCH_COUNT,
                IAnalyticsFilterItem.BaseType.ROCKET);
        mDataAnalytics = new ArrayList<>();
        mDataAnalytics.add(new DataAnalytics("value1", "base1"));
        mDataAnalytics.add(new DataAnalytics("value2", "base2"));
        mDataAnalytics.add(new DataAnalytics("value3", "base3"));
    }

    private void initDataLaunchTwo() {
        mDataLaunchTwo = new DataLaunch();
        mDataLaunchTwo.setFlight_number(2);
        mDataLaunchTwo.setMission_name("Mission name2");
        mDataLaunchTwo.setRocket_name("Rocket name2");
        mDataLaunchTwo.setLaunch_year("2002");
        mDataLaunchTwo.setLaunch_date_utc("date UTC2");
        mDataLaunchTwo.setLaunch_date_unix(1543807212);
        mDataLaunchTwo.setMission_patch_small("http://site.ru/patch.jpeg2");
        mDataLaunchTwo.setDetails("mission details2");
        mDataLaunchTwo.setImageId(2);
        mDataLaunchTwo.setImage(mImageTwo);
        mDataLaunchTwo.setPresskit("presskit2");
        mDataLaunchTwo.setArticle_link("article link2");
        mDataLaunchTwo.setVideo_link("video link2");
        mDataLaunchTwo.setWikipedia("wikipedia2");
        mDataLaunchTwo.setFlickr_images(new ArrayList<>());
        mDataLaunchTwo.getFlickr_images().add("flickr image2");
        mDataLaunchTwo.setPayload_mass_kg_sum(102);
        mDataLaunchTwo.setNationality("nation2");
        mDataLaunchTwo.setOrbit("LRO2");
    }

    private void initDataLaunchOne() {
        mDataLaunchOne = new DataLaunch();
        mDataLaunchOne.setFlight_number(1);
        mDataLaunchOne.setMission_name("Mission name");
        mDataLaunchOne.setRocket_name("Rocket name");
        mDataLaunchOne.setLaunch_year("2008");
        mDataLaunchOne.setLaunch_date_utc("date UTC");
        mDataLaunchOne.setLaunch_date_unix(1543807211);
        mDataLaunchOne.setMission_patch_small("http://site.ru/patch.jpeg");
        mDataLaunchOne.setDetails("mission details");
        mDataLaunchOne.setImageId(1);
        mDataLaunchOne.setImage(mImageOne);
        mDataLaunchOne.setPresskit("presskit");
        mDataLaunchOne.setArticle_link("article link");
        mDataLaunchOne.setVideo_link("video link");
        mDataLaunchOne.setWikipedia("wikipedia");
        mDataLaunchOne.setFlickr_images(new ArrayList<>());
        mDataLaunchOne.getFlickr_images().add("flickr image");
        mDataLaunchOne.setPayload_mass_kg_sum(100);
        mDataLaunchOne.setNationality("nation");
        mDataLaunchOne.setOrbit("LRO");
    }

    @Test
    public void convertLaunch() {
        DomainLaunch domainLaunch = DataToDomainConverter.convertLaunch(mDataLaunchOne);
        compareLaunchOne(domainLaunch);
    }

    @Test
    public void convertLaunchList() {
        List<DomainLaunch> domainLaunches = DataToDomainConverter.convertLaunchList(mDataLaunches);
        assertEquals(2, domainLaunches.size());
        compareLaunchOne(domainLaunches.get(0));
        compareLaunchTwo(domainLaunches.get(1));
    }

    @Test
    public void convertAnalytics() {
        DomainAnalytics domainAnalytics = DataToDomainConverter.convertAnalytics(mDataAnalytics, mAnalyticsFilter);
        assertEquals(domainAnalytics.getItemType(), IAnalyticsFilter.ItemType.LAUNCH_COUNT);
        assertEquals(domainAnalytics.getBaseType(), IAnalyticsFilterItem.BaseType.ROCKET);
        List<DomainAnalyticsItem> items = domainAnalytics.getItems();
        assertEquals(3, items.size());
        for (int i = 1; i <= 3; i++) {
            assertEquals("value" + i, items.get(i - 1).getValue());
            assertEquals("base" + i, items.get(i - 1).getBase());
        }
    }


    private void compareLaunchOne(DomainLaunch domainLaunch) {
        assertEquals(1, domainLaunch.getFlight_number());
        assertEquals("Mission name", domainLaunch.getMission_name());
        assertEquals("Rocket name", domainLaunch.getRocket_name());
        assertEquals("2008", domainLaunch.getLaunch_year());
        assertEquals("date UTC", domainLaunch.getLaunch_date_utc());
        assertEquals(1543807211, domainLaunch.getLaunch_date_unix());
        assertEquals("http://site.ru/patch.jpeg", domainLaunch.getMission_patch_small());
        assertEquals("mission details", domainLaunch.getDetails());
        assertEquals(1, domainLaunch.getImageId());
        assertEquals(mImageOne, domainLaunch.getImage());
        assertEquals("presskit", domainLaunch.getPresskit());
        assertEquals("article link", domainLaunch.getArticle_link());
        assertEquals("video link", domainLaunch.getVideo_link());
        assertEquals("wikipedia", domainLaunch.getWikipedia());
        assertEquals(1, domainLaunch.getFlickr_images().size());
        assertEquals("flickr image", domainLaunch.getFlickr_images().get(0));
        assertEquals(100, (int) domainLaunch.getPayload_mass_kg_sum());
        assertEquals("nation", domainLaunch.getNationality());
        assertEquals("LRO", domainLaunch.getOrbit());
    }

    private void compareLaunchTwo(DomainLaunch domainLaunch) {
        assertEquals(2, domainLaunch.getFlight_number());
        assertEquals("Mission name2", domainLaunch.getMission_name());
        assertEquals("Rocket name2", domainLaunch.getRocket_name());
        assertEquals("2002", domainLaunch.getLaunch_year());
        assertEquals("date UTC2", domainLaunch.getLaunch_date_utc());
        assertEquals(1543807212, domainLaunch.getLaunch_date_unix());
        assertEquals("http://site.ru/patch.jpeg2", domainLaunch.getMission_patch_small());
        assertEquals("mission details2", domainLaunch.getDetails());
        assertEquals(2, domainLaunch.getImageId());
        assertEquals(mImageTwo, domainLaunch.getImage());
        assertEquals("presskit2", domainLaunch.getPresskit());
        assertEquals("article link2", domainLaunch.getArticle_link());
        assertEquals("video link2", domainLaunch.getVideo_link());
        assertEquals("wikipedia2", domainLaunch.getWikipedia());
        assertEquals(1, domainLaunch.getFlickr_images().size());
        assertEquals("flickr image2", domainLaunch.getFlickr_images().get(0));
        assertEquals(102, (int) domainLaunch.getPayload_mass_kg_sum());
        assertEquals("nation2", domainLaunch.getNationality());
        assertEquals("LRO2", domainLaunch.getOrbit());
    }

    @After
    public void tearDown() throws Exception {
        mDataLaunchOne = null;
        mImageOne = null;
        mDataLaunchTwo = null;
        mImageTwo = null;
        mAnalyticsFilter = null;
        mDataAnalytics = null;
    }

}