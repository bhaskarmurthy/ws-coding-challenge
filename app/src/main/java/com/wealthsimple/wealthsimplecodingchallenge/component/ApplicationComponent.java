package com.wealthsimple.wealthsimplecodingchallenge.component;

import com.wealthsimple.wealthsimplecodingchallenge.module.NetworkModule;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleAPI;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by bhaskar on 2016-03-01
 */
@Singleton
@Component(modules = NetworkModule.class)
public interface ApplicationComponent {
    WealthsimpleAPI api();
}
