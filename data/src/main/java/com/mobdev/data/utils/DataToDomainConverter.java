package com.mobdev.data.utils;

import com.mobdev.data.model.DataAnalytics;
import com.mobdev.data.model.DataLaunch;
import com.mobdev.domain.model.analytics.DomainAnalytics;
import com.mobdev.domain.model.analytics.DomainAnalyticsItem;
import com.mobdev.domain.model.filter.IAnalyticsFilter;
import com.mobdev.domain.model.launch.DomainLaunch;

import java.util.ArrayList;
import java.util.List;

public final class DataToDomainConverter {

    public static List<DomainLaunch> convertLaunchList(List<DataLaunch> dataLaunches) {
        List<DomainLaunch> domainLaunches = new ArrayList<>();
        for (DataLaunch launch : dataLaunches) {
            domainLaunches.add(convertLaunch(launch));
        }
        return domainLaunches;
    }


    public static DomainLaunch convertLaunch(DataLaunch launch) {
        if (launch != null) {
            DomainLaunch domainLaunch = new DomainLaunch();
            domainLaunch.setFlight_number(launch.getFlight_number());
            domainLaunch.setMission_name(launch.getMission_name());
            domainLaunch.setRocket_name(launch.getRocket_name());
            domainLaunch.setLaunch_year(launch.getLaunch_year());
            domainLaunch.setLaunch_date_utc(launch.getLaunch_date_utc());
            domainLaunch.setLaunch_date_unix(launch.getLaunch_date_unix());
            domainLaunch.setMission_patch_small(launch.getMission_patch_small());
            domainLaunch.setDetails(launch.getDetails());
            domainLaunch.setImageId(launch.getImageId());
            domainLaunch.setImage(launch.getImage());
            domainLaunch.setPresskit(launch.getPresskit());
            domainLaunch.setArticle_link(launch.getArticle_link());
            domainLaunch.setVideo_link(launch.getVideo_link());
            domainLaunch.setWikipedia(launch.getWikipedia());
            domainLaunch.setFlickr_images(new ArrayList<>());
            domainLaunch.getFlickr_images().addAll(launch.getFlickr_images());
            domainLaunch.setPayload_mass_kg_sum(launch.getPayload_mass_kg_sum());
            domainLaunch.setNationality(launch.getNationality());
            domainLaunch.setOrbit(launch.getOrbit());
            return domainLaunch;
        }
        return null;
    }

    public static DomainAnalytics convertAnalytics(List<DataAnalytics> dataAnalyticsList, IAnalyticsFilter analyticsFilter) {
        if (dataAnalyticsList != null && analyticsFilter != null && analyticsFilter.getItemsCount() == 1) {
            DomainAnalytics domainAnalytics = new DomainAnalytics();
            domainAnalytics.setItemType(analyticsFilter.getItem(0).getType());
            domainAnalytics.setBaseType(analyticsFilter.getItem(0).getBaseType());
            List<DomainAnalyticsItem> items = new ArrayList<>();
            for (DataAnalytics dataAnalytics : dataAnalyticsList) {
                items.add(new DomainAnalyticsItem(dataAnalytics.getValue(), dataAnalytics.getBase()));
            }
            domainAnalytics.setItems(items);
            return domainAnalytics;
        } else {
            throw new IllegalArgumentException("dataAnalyticsList and analyticsFilter can't be null, analyticsFilter.getItemsCount()=1");
        }
    }

}
