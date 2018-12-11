package com.mobdev.data.utils;

import androidx.annotation.Nullable;

import com.mobdev.data.model.DataLaunch;
import com.mobdev.data.model.ServerResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class DataConverterFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        final Type listDataLunchType = TypeToken.getParameterized(List.class, DataLaunch.class).getType();
        final Type responseType = TypeToken.get(ServerResponse.class).getType();
        Type launchTypeToken;

        if (type.equals(listDataLunchType)) {
            launchTypeToken = TypeToken.getParameterized(List.class, responseType).getType();
        } else {
            launchTypeToken = responseType;
        }

        final Converter<ResponseBody, List<ServerResponse>> listConverter = retrofit.nextResponseBodyConverter(this, launchTypeToken, annotations);
        final Converter<ResponseBody, ServerResponse> itemConverter = retrofit.nextResponseBodyConverter(this, launchTypeToken, annotations);

        return (Converter<ResponseBody, Object>) value -> {

            if (type.equals(listDataLunchType)) {
                List<ServerResponse> serverResponseList = listConverter.convert(value);
                List<DataLaunch> dataLaunches = new ArrayList<>();

                for (ServerResponse serverResponse : serverResponseList) {
                    DataLaunch dataLaunch = new DataLaunch(serverResponse);
                    dataLaunches.add(dataLaunch);

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
