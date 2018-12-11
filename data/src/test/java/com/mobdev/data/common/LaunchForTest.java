package com.mobdev.data.common;

import com.mobdev.data.model.DataAnalytics;
import com.mobdev.data.model.DataLaunch;
import com.mobdev.domain.model.analytics.DomainAnalytics;
import com.mobdev.domain.model.analytics.DomainAnalyticsItem;
import com.mobdev.domain.model.filter.AnalyticsFilter;
import com.mobdev.domain.model.filter.IAnalyticsFilter;
import com.mobdev.domain.model.filter.IAnalyticsFilterItem;
import com.mobdev.domain.model.launch.DomainLaunch;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LaunchForTest {
    private DataLaunch mDataLaunchOne;
    private DataLaunch mDataLaunchTwo;
    private List<DataLaunch> mDataLaunches = new ArrayList<>();
    private byte[] mImageOne;
    private byte[] mImageTwo;
    private DomainLaunch mDomainLaunchOne;
    private DomainLaunch mDomainLaunchTwo;
    private List<DomainLaunch> mDomainLaunches = new ArrayList<>();
    private List<DataAnalytics> mDataAnalytics;
    private IAnalyticsFilter mAnalyticsFilter;

    public LaunchForTest() {

        mImageOne = new byte[1];
        mImageTwo = new byte[1];

        initDataLaunchOne();
        initDataLaunchTwo();
        mDataLaunches.add(mDataLaunchOne);
        mDataLaunches.add(mDataLaunchTwo);

        initDomainLaunchOne();
        initDomainLaunchTwo();
        mDomainLaunches.add(mDomainLaunchOne);
        mDomainLaunches.add(mDomainLaunchTwo);

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

    public void compareLaunchOne(DomainLaunch domainLaunch) {
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

    public void compareLaunchTwo(DomainLaunch domainLaunch) {
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

    private void initDomainLaunchTwo() {
        mDomainLaunchTwo = new DomainLaunch();
        mDomainLaunchTwo.setFlight_number(2);
        mDomainLaunchTwo.setMission_name("Mission name2");
        mDomainLaunchTwo.setRocket_name("Rocket name2");
        mDomainLaunchTwo.setLaunch_year("2002");
        mDomainLaunchTwo.setLaunch_date_utc("date UTC2");
        mDomainLaunchTwo.setLaunch_date_unix(1543807212);
        mDomainLaunchTwo.setMission_patch_small("http://site.ru/patch.jpeg2");
        mDomainLaunchTwo.setDetails("mission details2");
        mDomainLaunchTwo.setImageId(2);
        mDomainLaunchTwo.setImage(mImageTwo);
        mDomainLaunchTwo.setPresskit("presskit2");
        mDomainLaunchTwo.setArticle_link("article link2");
        mDomainLaunchTwo.setVideo_link("video link2");
        mDomainLaunchTwo.setWikipedia("wikipedia2");
        mDomainLaunchTwo.setFlickr_images(new ArrayList<>());
        mDomainLaunchTwo.getFlickr_images().add("flickr image2");
        mDomainLaunchTwo.setPayload_mass_kg_sum(102);
        mDomainLaunchTwo.setNationality("nation2");
        mDomainLaunchTwo.setOrbit("LRO2");
    }

    private void initDomainLaunchOne() {
        mDomainLaunchOne = new DomainLaunch();
        mDomainLaunchOne.setFlight_number(1);
        mDomainLaunchOne.setMission_name("Mission name");
        mDomainLaunchOne.setRocket_name("Rocket name");
        mDomainLaunchOne.setLaunch_year("2008");
        mDomainLaunchOne.setLaunch_date_utc("date UTC");
        mDomainLaunchOne.setLaunch_date_unix(1543807211);
        mDomainLaunchOne.setMission_patch_small("http://site.ru/patch.jpeg");
        mDomainLaunchOne.setDetails("mission details");
        mDomainLaunchOne.setImageId(1);
        mDomainLaunchOne.setImage(mImageOne);
        mDomainLaunchOne.setPresskit("presskit");
        mDomainLaunchOne.setArticle_link("article link");
        mDomainLaunchOne.setVideo_link("video link");
        mDomainLaunchOne.setWikipedia("wikipedia");
        mDomainLaunchOne.setFlickr_images(new ArrayList<>());
        mDomainLaunchOne.getFlickr_images().add("flickr image");
        mDomainLaunchOne.setPayload_mass_kg_sum(100);
        mDomainLaunchOne.setNationality("nation");
        mDomainLaunchOne.setOrbit("LRO");
    }

    public void compareLaunchOne(DataLaunch dataLaunch) {
        assertEquals(1, dataLaunch.getFlight_number());
        assertEquals("Mission name", dataLaunch.getMission_name());
        assertEquals("Rocket name", dataLaunch.getRocket_name());
        assertEquals("2008", dataLaunch.getLaunch_year());
        assertEquals("date UTC", dataLaunch.getLaunch_date_utc());
        assertEquals(1543807211, dataLaunch.getLaunch_date_unix());
        assertEquals("http://site.ru/patch.jpeg", dataLaunch.getMission_patch_small());
        assertEquals("mission details", dataLaunch.getDetails());
        assertEquals(1, dataLaunch.getImageId());
        assertEquals(mImageOne, dataLaunch.getImage());
        assertEquals("presskit", dataLaunch.getPresskit());
        assertEquals("article link", dataLaunch.getArticle_link());
        assertEquals("video link", dataLaunch.getVideo_link());
        assertEquals("wikipedia", dataLaunch.getWikipedia());
        assertEquals(1, dataLaunch.getFlickr_images().size());
        assertEquals("flickr image", dataLaunch.getFlickr_images().get(0));
        assertEquals(100, (int) dataLaunch.getPayload_mass_kg_sum());
        assertEquals("nation", dataLaunch.getNationality());
        assertEquals("LRO", dataLaunch.getOrbit());
    }

    public void compareLaunchTwo(DataLaunch dataLaunch) {
        assertEquals(2, dataLaunch.getFlight_number());
        assertEquals("Mission name2", dataLaunch.getMission_name());
        assertEquals("Rocket name2", dataLaunch.getRocket_name());
        assertEquals("2002", dataLaunch.getLaunch_year());
        assertEquals("date UTC2", dataLaunch.getLaunch_date_utc());
        assertEquals(1543807212, dataLaunch.getLaunch_date_unix());
        assertEquals("http://site.ru/patch.jpeg2", dataLaunch.getMission_patch_small());
        assertEquals("mission details2", dataLaunch.getDetails());
        assertEquals(2, dataLaunch.getImageId());
        assertEquals(mImageTwo, dataLaunch.getImage());
        assertEquals("presskit2", dataLaunch.getPresskit());
        assertEquals("article link2", dataLaunch.getArticle_link());
        assertEquals("video link2", dataLaunch.getVideo_link());
        assertEquals("wikipedia2", dataLaunch.getWikipedia());
        assertEquals(1, dataLaunch.getFlickr_images().size());
        assertEquals("flickr image2", dataLaunch.getFlickr_images().get(0));
        assertEquals(102, (int) dataLaunch.getPayload_mass_kg_sum());
        assertEquals("nation2", dataLaunch.getNationality());
        assertEquals("LRO2", dataLaunch.getOrbit());
    }
    public void compareDomainLaunches(List<DomainLaunch> domainLaunches){
        assertEquals(2, domainLaunches.size());
        compareLaunchOne(domainLaunches.get(0));
        compareLaunchTwo(domainLaunches.get(1));
    }
    public void compareAnalytics(DomainAnalytics domainAnalytics){
        assertEquals(domainAnalytics.getItemType(), IAnalyticsFilter.ItemType.LAUNCH_COUNT);
        assertEquals(domainAnalytics.getBaseType(), IAnalyticsFilterItem.BaseType.ROCKET);
        List<DomainAnalyticsItem> items = domainAnalytics.getItems();
        assertEquals(3, items.size());
        for (int i = 1; i <= 3; i++) {
            assertEquals("value" + i, items.get(i - 1).getValue());
            assertEquals("base" + i, items.get(i - 1).getBase());
        }
    }

    public void tearDown() throws Exception {
        mDataLaunchOne = null;
        mImageOne = null;
        mDataLaunchTwo = null;
        mImageTwo = null;
        mDataLaunches = null;
        mDomainLaunchOne = null;
        mDomainLaunchTwo = null;
        mDomainLaunches = null;
        mAnalyticsFilter = null;
        mDataAnalytics = null;
    }

    public DataLaunch getDataLaunchOne() {
        return mDataLaunchOne;
    }

    public DataLaunch getDataLaunchTwo() {
        return mDataLaunchTwo;
    }

    public List<DataLaunch> getDataLaunches() {
        return mDataLaunches;
    }

    public byte[] getImageOne() {
        return mImageOne;
    }

    public byte[] getImageTwo() {
        return mImageTwo;
    }

    public DomainLaunch getDomainLaunchOne() {
        return mDomainLaunchOne;
    }

    public DomainLaunch getDomainLaunchTwo() {
        return mDomainLaunchTwo;
    }

    public List<DomainLaunch> getDomainLaunches() {
        return mDomainLaunches;
    }

    public List<DataAnalytics> getDataAnalytics() {
        return mDataAnalytics;
    }

    public IAnalyticsFilter getAnalyticsFilter() {
        return mAnalyticsFilter;
    }
}
