package com.example.data.api.converter;

import android.graphics.Bitmap;

import com.example.data.model.DataLaunch;
import com.example.data.model.DataLaunchCache;
import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.example.domain.model.launch.DomainLaunchCache;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public final class DomainToDataConverter {

    public static List<DataLaunch> convertLaunchList(List<DomainLaunch> domainLaunches) {
        List<DataLaunch> dataLaunches = new ArrayList<>();
        for (DomainLaunch launch : domainLaunches) {
            dataLaunches.add(convertLaunch(launch));
        }
        return dataLaunches;
    }

    public static List<DataLaunchCache> convertLaunchCacheList(List<DomainLaunchCache> domainLaunches) {
        List<DataLaunchCache> dataLaunches = new ArrayList<>();
        for (DomainLaunchCache launch : domainLaunches) {
            dataLaunches.add(convertLaunchCache(launch));
        }
        return dataLaunches;
    }


    public static DataLaunch convertLaunch(DomainLaunch launch) {
        if (launch != null) {
            DataLaunch dataLaunch = new DataLaunch();
            dataLaunch.setFlight_number(launch.getFlight_number());
            dataLaunch.setMission_name(launch.getMission_name());
            dataLaunch.setRocket_name(launch.getRocket_name());
            dataLaunch.setLaunch_date_utc(launch.getLaunch_date_utc());
            dataLaunch.setLaunch_date_unix(launch.getLaunch_date_unix());
            dataLaunch.setMission_patch_small(launch.getMission_patch_small());
            dataLaunch.setDetails(launch.getDetails());
            dataLaunch.setImageId(launch.getImageId());
            dataLaunch.setCache(launch.isCache());
            dataLaunch.setImage(launch.getImage());

            return dataLaunch;
        } else {
            return null;
        }
    }

    public static DataLaunchCache convertLaunchCache(DomainLaunchCache launch) {
        if (launch != null) {
            DataLaunchCache dataLaunch = new DataLaunchCache();
            dataLaunch.setFlight_number(launch.getFlight_number());
            return dataLaunch;
        } else {
            return null;
        }
    }

}
