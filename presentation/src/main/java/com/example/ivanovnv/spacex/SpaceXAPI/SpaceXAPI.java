package com.example.ivanovnv.spacex.SpaceXAPI;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface SpaceXAPI {

    @GET("launches")
    Single<List<Launch>> getAllPastLaunches();
}
