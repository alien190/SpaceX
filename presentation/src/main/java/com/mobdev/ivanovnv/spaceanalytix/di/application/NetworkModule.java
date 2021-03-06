package com.mobdev.ivanovnv.spaceanalytix.di.application;

import com.mobdev.data.BuildConfig;
import com.mobdev.data.api.SpaceXAPI;
import com.mobdev.data.utils.DataConverterFactory;
import com.mobdev.data.repository.LaunchRemoteRepository;
import com.mobdev.domain.repository.ILaunchRepository;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import toothpick.config.Module;

public class NetworkModule extends Module {
    private SpaceXAPI mApi;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private Gson mGson;
    private ILaunchRepository mLaunchRepositoryRemote;


    public NetworkModule() {
        mLaunchRepositoryRemote = new LaunchRemoteRepository(provideApi(), provideOkHttpClient());
        bind(ILaunchRepository.class).withName(ILaunchRepository.REMOTE).toInstance(mLaunchRepositoryRemote);
    }

    public SpaceXAPI provideApi() {
        if (mApi == null) mApi = provideRetrofit().create(SpaceXAPI.class);
        return mApi;
    }

    private OkHttpClient provideOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return mOkHttpClient;
    }

    private Retrofit provideRetrofit() {
        if (mGson == null) mGson = new Gson();

        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_URL)
                    .client(provideOkHttpClient())
                    .addConverterFactory(new DataConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
