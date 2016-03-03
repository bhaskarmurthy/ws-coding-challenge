package com.wealthsimple.wealthsimplecodingchallenge.component;

import com.wealthsimple.wealthsimplecodingchallenge.module.ActivityModule;
import com.wealthsimple.wealthsimplecodingchallenge.module.MainModule;
import com.wealthsimple.wealthsimplecodingchallenge.presenter.MainPresenter;
import com.wealthsimple.wealthsimplecodingchallenge.view.MainActivity;

import dagger.Component;

/**
 * Created by bhaskar on 2016-03-01
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                MainModule.class
        }
)
public interface MainComponent {
    void inject(MainActivity activity);
    void inject(MainPresenter presenter);
}
