package com.ivanovnv.data.api;

import com.ivanovnv.data.model.DataLaunch;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpaceXAPI {
    @GET("launches/past?sort=launch_date_utc&order=DESC")
    Single<List<DataLaunch>> getAllPastLaunches();

    @GET("launches/{flight_number}")
    Single<DataLaunch> getLaunchByFlightNumber(@Path("flight_number") Integer flight_number);
}
