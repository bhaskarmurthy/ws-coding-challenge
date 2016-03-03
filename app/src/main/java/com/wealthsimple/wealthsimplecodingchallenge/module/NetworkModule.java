package com.wealthsimple.wealthsimplecodingchallenge.module;

import com.wealthsimple.wealthsimplecodingchallenge.BuildConfig;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleAPI;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleGson;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by bhaskar on 2016-03-01
 */
@Module
public class NetworkModule {

    private final String mRootUrl;
    private final String mToken;

    public NetworkModule(String rootUrl, String token) {
        mRootUrl = rootUrl;
        mToken = token;
    }

    @Provides
    public OkHttpClient provideHttpClient() {
        // set up http logging
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return client;
    }

    @Provides
    public WealthsimpleAPI provideApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(mRootUrl)
                .addConverterFactory(GsonConverterFactory.create(WealthsimpleGson.instance()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return new WealthsimpleAPI(retrofit.create(WealthsimpleService.class), WealthsimpleGson.instance(), mToken);
    }
}
