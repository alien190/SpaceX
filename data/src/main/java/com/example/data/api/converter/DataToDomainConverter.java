package com.example.data.api.converter;

import com.example.data.model.DataLaunch;
import com.example.domain.model.launch.DomainLaunch;

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
            return domainLaunch;
        }
        return null;
    }



}
