package com.example.ivanovnv.spacex.SpaceXAPI.converter;

import android.support.annotation.Nullable;

import com.example.ivanovnv.spacex.SpaceXAPI.Launch;
import com.example.ivanovnv.spacex.SpaceXAPI.Response;
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
        Type responseType = TypeToken.get(Response.class).getType();
        Type launchTypeToken = TypeToken.getParameterized(List.class, responseType).getType();
        final Converter<ResponseBody, List<Response>> converter = retrofit.nextResponseBodyConverter(this, launchTypeToken, annotations);
        return (Converter<ResponseBody, Object>) value -> {
            List<Response> responseList = converter.convert(value);
            List<Launch> launches = new ArrayList<>();

            for (Response response : responseList) {
                launches.add(new Launch(response));
            }
            return launches;
        };
    }
}
