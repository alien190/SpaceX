package com.example.data.api.converter;

import com.example.data.model.DataLaunch;
import com.example.domain.model.launch.DomainLaunch;

import java.util.ArrayList;
import java.util.List;

public final class DomainToDataConverter {

    public static List<DataLaunch> convertLaunch(List<DomainLaunch> domainLaunches) {
        List<DataLaunch> dataLaunches = new ArrayList<>();
        for (DomainLaunch launch : domainLaunches) {

            DataLaunch dataLaunch = new DataLaunch();
            dataLaunch.setFlight_number(launch.getFlight_number());
            dataLaunch.setMission_name(launch.getMission_name());
            dataLaunch.setRocket_name(launch.getRocket_name());
            dataLaunch.setLaunch_date_utc(launch.getLaunch_date_utc());
            dataLaunch.setMission_patch_small(launch.getMission_patch_small());
            dataLaunch.setDetails(launch.getDetails());
            dataLaunches.add(dataLaunch);
        }
        return dataLaunches;
    }

}
