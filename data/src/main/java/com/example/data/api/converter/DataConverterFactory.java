package com.example.data.api.converter;

import android.support.annotation.Nullable;

import com.example.data.model.DataLaunch;
import com.example.data.model.DataLaunchCache;
import com.example.data.model.ServerResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import timber.log.Timber;

public class DataConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        //final Type dataLunchType = TypeToken.get(DataLaunch.class).getType();

        final Type listDataLunchType = TypeToken.getParameterized(List.class, DataLaunch.class).getType();
        final Type listDataLunchCacheType = TypeToken.getParameterized(List.class, DataLaunchCache.class).getType();
        final Type responseType = TypeToken.get(ServerResponse.class).getType();
        Type launchTypeToken;

        if(type.equals(listDataLunchCacheType) || type.equals(listDataLunchType)) {
            launchTypeToken = TypeToken.getParameterized(List.class, responseType).getType();
        } else {
            launchTypeToken = responseType;
        }

        final Converter<ResponseBody, List<ServerResponse>> listConverter = retrofit.nextResponseBodyConverter(this, launchTypeToken, annotations);
        final Converter<ResponseBody, ServerResponse> itemConverter = retrofit.nextResponseBodyConverter(this, launchTypeToken, annotations);

        return (Converter<ResponseBody, Object>) value -> {

            if(type.equals(listDataLunchType)) {
                List<ServerResponse> serverResponseList = listConverter.convert(value);
                List<DataLaunch> dataLaunches = new ArrayList<>();

                for (ServerResponse serverResponse : serverResponseList) {
                    DataLaunch dataLaunch = new DataLaunch(serverResponse);
                  //  dataLaunch.setMission_patch_small("bla-bla-bla-path");
                    dataLaunches.add(dataLaunch);

                }
                return dataLaunches;
            } else if(type.equals(listDataLunchCacheType)) {
                List<ServerResponse> serverResponseList = listConverter.convert(value);
                List<DataLaunchCache> dataLaunches = new ArrayList<>();

                for (ServerResponse serverResponse : serverResponseList) {
                    dataLaunches.add(new DataLaunchCache(serverResponse));
                }
                return dataLaunches;
            } else {
                ServerResponse serverResponse = itemConverter.convert(value);
                DataLaunch dataLaunch = new DataLaunch(serverResponse);
                return dataLaunch;
            }
        };
    }
}
