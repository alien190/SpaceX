package com.example.data.api.converter;

import com.example.data.model.DataLaunch;
import com.example.domain.model.launch.DomainLaunch;

import java.util.ArrayList;
import java.util.List;

public final class DataToDomainConverter {

    public static List<DomainLaunch> converLaunch(List<DataLaunch> dataLaunches) {
        List<DomainLaunch> domainLaunches = new ArrayList<>();
        for (DataLaunch launch : dataLaunches) {
            DomainLaunch domainLaunch = new DomainLaunch();
            domainLaunch.setFlight_number(launch.getFlight_number());
            domainLaunch.setMission_name(launch.getMission_name());
            domainLaunch.setRocket_name(launch.getRocket_name());
            domainLaunch.setLaunch_date_utc(launch.getLaunch_date_utc());
            domainLaunch.setMission_patch_small(launch.getMission_patch_small());
            domainLaunches.add(domainLaunch);
        }
        return domainLaunches;
    }

}
