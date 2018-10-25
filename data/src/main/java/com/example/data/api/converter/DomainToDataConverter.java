package com.example.data.api.converter;

import android.graphics.Bitmap;

import com.example.data.model.DataLaunch;
import com.example.data.utils.DbBitmapUtility;
import com.example.domain.model.launch.DomainLaunch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public final class DomainToDataConverter {

    public static List<DataLaunch> convertLaunch(List<DomainLaunch> domainLaunches) {
        List<DataLaunch> dataLaunches = new ArrayList<>();
        for (DomainLaunch launch : domainLaunches) {

            DataLaunch dataLaunch = new DataLaunch();
            dataLaunch.setFlight_number(launch.getFlight_number());
            dataLaunch.setMission_name(launch.getMission_name());
            dataLaunch.setRocket_name(launch.getRocket_name());
            dataLaunch.setLaunch_date_utc(launch.getLaunch_date_utc());
            dataLaunch.setLaunch_date_unix(launch.getLaunch_date_unix());
            dataLaunch.setMission_patch_small(launch.getMission_patch_small());
            dataLaunch.setDetails(launch.getDetails());
            try {
                Bitmap bitmap = Picasso.get().load(launch.getMission_patch_small()).get();
                byte[] bytes = DbBitmapUtility.getBytes(bitmap);
                dataLaunch.setImage(bytes);
            } catch (Throwable throwable) {
                Timber.d(throwable);
            }
            dataLaunches.add(dataLaunch);
        }
        return dataLaunches;
    }

}
