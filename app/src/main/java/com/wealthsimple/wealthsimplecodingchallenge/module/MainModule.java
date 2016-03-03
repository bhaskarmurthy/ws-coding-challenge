package com.wealthsimple.wealthsimplecodingchallenge.module;

import com.wealthsimple.wealthsimplecodingchallenge.presenter.MainPresenter;
import com.wealthsimple.wealthsimplecodingchallenge.view.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by bhaskar on 2016-03-01
 */
@Module
public class MainModule {

    private MainView mView;

    public MainModule(MainView view) {
        mView = view;
    }

    @Provides
    public MainPresenter providePresenter() {
        return new MainPresenter();
    }

    @Provides
    public MainView provideView() {
        return mView;
    }
}
