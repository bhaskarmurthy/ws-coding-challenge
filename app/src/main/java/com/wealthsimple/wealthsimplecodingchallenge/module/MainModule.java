package com.wealthsimple.wealthsimplecodingchallenge.module;

import com.wealthsimple.wealthsimplecodingchallenge.presenter.MainPresenter;
import com.wealthsimple.wealthsimplecodingchallenge.view.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Module to provide instances related to Main activity
 */
@Module
public class MainModule {

    private MainView mView;

    /**
     * Constructor
     * @param view View interface bound to this module
     */
    public MainModule(MainView view) {
        mView = view;
    }

    /**
     * Provide presenter for Main component
     * @return Presenter for Main component
     */
    @Provides
    public MainPresenter providePresenter() {
        return new MainPresenter();
    }

    /**
     * Provide view interface for Main component
     * @return View interface for Main component
     */
    @Provides
    public MainView provideView() {
        return mView;
    }
}
