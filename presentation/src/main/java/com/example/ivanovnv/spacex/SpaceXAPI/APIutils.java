package com.example.ivanovnv.spacex.SpaceXAPI;

import com.example.data.api.converter.DataConverterFactory;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIutils {

    private static SpaceXAPI api;
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    private static Gson gson;

    public static SpaceXAPI getApi() {
        if (api == null) api = getRetrofit().create(SpaceXAPI.class);
        return api;
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return okHttpClient;
    }

    private static Retrofit getRetrofit() {
        if (gson == null) gson = new Gson();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    //.baseUrl(BuildConfig.SERVER_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(new DataConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
