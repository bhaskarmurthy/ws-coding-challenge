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
 * Module to provide instances related to network operations
 */
@Module
public class NetworkModule {

    private final String mRootUrl;
    private final String mToken;

    /**
     * Constructor
     *
     * @param rootUrl Root url of API
     * @param token User token
     */
    public NetworkModule(String rootUrl, String token) {
        mRootUrl = rootUrl;
        mToken = token;
    }

    /**
     * Provide an HTTP client for use in API
     * @return HTTP client
     */
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

    /**
     * Provide a client to make requests on API service
     * @param client {@link OkHttpClient} to use in api client
     * @return API client
     */
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
