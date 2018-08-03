package com.example.data.api;

import com.example.data.model.Launch;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface SpaceXAPI {

    @GET("launches")
    Single<List<Launch>> getAllPastLaunches();
}
