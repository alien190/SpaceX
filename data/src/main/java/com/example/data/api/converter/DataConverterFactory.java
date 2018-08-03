package com.example.data.api.converter;

import android.support.annotation.Nullable;

import com.example.data.model.Launch;
import com.example.data.model.ServerResponse;
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
        Type responseType = TypeToken.get(ServerResponse.class).getType();
        Type launchTypeToken = TypeToken.getParameterized(List.class, responseType).getType();
        final Converter<ResponseBody, List<ServerResponse>> converter = retrofit.nextResponseBodyConverter(this, launchTypeToken, annotations);
        return (Converter<ResponseBody, Object>) value -> {
            List<ServerResponse> serverResponseList = converter.convert(value);
            List<Launch> launches = new ArrayList<>();

            for (ServerResponse serverResponse : serverResponseList) {
                launches.add(new Launch(serverResponse));
            }
            return launches;
        };
    }
}
