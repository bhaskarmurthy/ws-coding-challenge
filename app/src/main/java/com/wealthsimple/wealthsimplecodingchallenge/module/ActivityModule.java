package com.wealthsimple.wealthsimplecodingchallenge.module;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Module to provide instances related to a single activity
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    /**
     * Constructor
     *
     * @param activity {@link Activity} represented by this module
     */
    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    /**
     * Provide activity context
     * @return {@link Context} for activity represented by this module
     */
    @Provides
    public Context provideContext() {
        return mActivity;
    }
}
