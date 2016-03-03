package com.wealthsimple.wealthsimplecodingchallenge.component;

import com.wealthsimple.wealthsimplecodingchallenge.module.NetworkModule;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleAPI;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Component to contain application-wide modules
 */
@Singleton
@Component(modules = NetworkModule.class)
public interface ApplicationComponent {
    WealthsimpleAPI api();
}
