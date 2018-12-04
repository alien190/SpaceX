package com.example.data.utils;

import com.example.data.model.DataLaunch;
import com.example.data.utils.DomainToDataConverter;
import com.example.domain.model.launch.DomainLaunch;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DomainToDataConverterTest {

    private DomainLaunch mDomainLaunchOne;
    private DomainLaunch mDomainLaunchTwo;
    private List<DomainLaunch> mDomainLaunches = new ArrayList<>();
    private byte[] mImageOne;
    private byte[] mImageTwo;

    @Before
    public void setUp() throws Exception {
        mImageOne = new byte[1];
        mImageTwo = new byte[1];

        initDomainLaunchOne();
        initDomainLaunchTwo();
        mDomainLaunches.add(mDomainLaunchOne);
        mDomainLaunches.add(mDomainLaunchTwo);
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


    @Test
    public void convertLaunchList() {
        List<DataLaunch> dataLaunches = DomainToDataConverter.convertLaunchList(mDomainLaunches);
        assertEquals(2, dataLaunches.size());
        compareLaunchOne(dataLaunches.get(0));
        compareLaunchTwo(dataLaunches.get(1));
    }

    @Test
    public void convertLaunch() {
        DataLaunch dataLaunch = DomainToDataConverter.convertLaunch(mDomainLaunchOne);
        compareLaunchOne(dataLaunch);
    }

    private void compareLaunchOne(DataLaunch dataLaunch) {
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

    private void compareLaunchTwo(DataLaunch dataLaunch) {
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

    @After
    public void tearDown() throws Exception {
        mDomainLaunchOne = null;
        mImageOne = null;
        mDomainLaunchTwo = null;
        mImageTwo = null;
    }
}