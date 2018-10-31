package com.example.data.api;

import com.example.data.model.DataLaunch;
import com.example.data.model.DataLaunchCache;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpaceXAPI {

    @GET("launches/past")
    Single<List<DataLaunch>> getAllPastLaunches();

    @GET("launches/{flight_number}")
    Single<DataLaunch> getLaunchByFlightNumber(@Path("flight_number") Integer flight_number);

    @GET("launches/past")
    Single<List<DataLaunchCache>> getAllPastLaunchesCache();
}
