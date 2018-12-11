package com.ivanovnv.data.utils;

import com.ivanovnv.data.model.DataLaunch;
import com.ivanovnv.domain.model.launch.DomainLaunch;

import java.util.ArrayList;
import java.util.List;

public final class DomainToDataConverter {

    public static List<DataLaunch> convertLaunchList(List<DomainLaunch> domainLaunches) {
        List<DataLaunch> dataLaunches = new ArrayList<>();
        for (DomainLaunch launch : domainLaunches) {
            dataLaunches.add(convertLaunch(launch));
        }
        return dataLaunches;
    }


    public static DataLaunch convertLaunch(DomainLaunch launch) {
        if (launch != null) {
            DataLaunch dataLaunch = new DataLaunch();
            dataLaunch.setFlight_number(launch.getFlight_number());
            dataLaunch.setMission_name(launch.getMission_name());
            dataLaunch.setRocket_name(launch.getRocket_name());
            dataLaunch.setLaunch_year(launch.getLaunch_year());
            dataLaunch.setLaunch_date_utc(launch.getLaunch_date_utc());
            dataLaunch.setLaunch_date_unix(launch.getLaunch_date_unix());
            dataLaunch.setMission_patch_small(launch.getMission_patch_small());
            dataLaunch.setDetails(launch.getDetails());
            dataLaunch.setImageId(launch.getImageId());
            dataLaunch.setImage(launch.getImage());
            dataLaunch.setPresskit(launch.getPresskit());
            dataLaunch.setArticle_link(launch.getArticle_link());
            dataLaunch.setVideo_link(launch.getVideo_link());
            dataLaunch.setWikipedia(launch.getWikipedia());
            dataLaunch.setFlickr_images(new ArrayList<>());
            dataLaunch.getFlickr_images().addAll(launch.getFlickr_images());
            dataLaunch.setPayload_mass_kg_sum(launch.getPayload_mass_kg_sum());
            dataLaunch.setNationality(launch.getNationality());
            dataLaunch.setOrbit(launch.getOrbit());
            return dataLaunch;
        } else {
            return null;
        }
    }


}
