package com.wealthsimple.wealthsimplecodingchallenge;

import android.app.Application;

import com.wealthsimple.wealthsimplecodingchallenge.component.ApplicationComponent;
import com.wealthsimple.wealthsimplecodingchallenge.component.DaggerApplicationComponent;
import com.wealthsimple.wealthsimplecodingchallenge.module.NetworkModule;

/**
 * Created by bhaskar on 2016-03-01
 */
public class WealthsimpleCodingChallengeApplication extends Application {
    private ApplicationComponent mComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule(BuildConfig.API_URL, BuildConfig.AUTH_TOKEN))
                .build();
    }

    public ApplicationComponent getComponent() {
        return mComponent;
    }
}
