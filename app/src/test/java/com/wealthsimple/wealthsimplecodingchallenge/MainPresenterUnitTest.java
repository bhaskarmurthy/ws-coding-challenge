package com.wealthsimple.wealthsimplecodingchallenge;

import android.text.TextUtils;
import android.util.Log;

import com.wealthsimple.wealthsimplecodingchallenge.model.Candidate;
import com.wealthsimple.wealthsimplecodingchallenge.model.Error;
import com.wealthsimple.wealthsimplecodingchallenge.presenter.MainPresenter;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleAPI;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleGson;
import com.wealthsimple.wealthsimplecodingchallenge.view.MainView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({
        TextUtils.class,
        Log.class,
        Schedulers.class,
        AndroidSchedulers.class
})
public class MainPresenterUnitTest {

    @Module
    class MainPresenterTestModule {
        @Provides
        public MainView provideView() {
            return mView;
        }

        @Provides
        public WealthsimpleAPI provideApi() {
            return mApi;
        }
    }

    @Component(modules = MainPresenterTestModule.class)
    interface MainPresenterTestComponent {
        void inject(MainPresenter presenter);
    }

    @Mock
    WealthsimpleAPI mApi;

    @Mock
    MainView mView;

    MainPresenter mPresenter;

    MainPresenterTestComponent mComponent;

    public MainPresenterUnitTest() {
        mockStatic(TextUtils.class);

        // use test text utils for isEmpty
        when(TextUtils.isEmpty(anyString())).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                final Object[] args = invocation.getArguments();
                return args == null || args.length <= 0 || TestTextUtils.isEmpty((CharSequence) args[0]);

            }
        });

        // mock loggers
        mockStatic(Log.class);
        when(Log.d(anyString(), anyString())).thenReturn(0);
        when(Log.e(anyString(), anyString(), Matchers.<Throwable>anyObject())).thenReturn(0);

        // use immediate scheduler for tests so that calls are run immediately on same thread
        final Scheduler scheduler = Schedulers.immediate();

        mockStatic(Schedulers.class);
        when(Schedulers.io()).thenReturn(scheduler);

        mockStatic(AndroidSchedulers.class);
        when(AndroidSchedulers.mainThread()).thenReturn(scheduler);

        mComponent = DaggerMainPresenterUnitTest_MainPresenterTestComponent.builder()
                .mainPresenterTestModule(new MainPresenterTestModule())
                .build();
    }

    @Before
    public void setUp() {
        initMocks(this);
        mPresenter = new MainPresenter();
        mComponent.inject(mPresenter);
    }

    @After
    public void tearDown() {
        mPresenter = null;
        mView = null;
    }

    @Test
    public void sendMessage_emptyCommand() throws Exception {
        mPresenter.sendCommand("");

        verify(mView).showError("Command required");
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void sendMessage_errorMessage() throws Exception {
        final String command = "invalid command";
        final String errorString = "{ \"error_type\":\"invalid_command\", \"error\":\"Command is not Marco\" }";
        final Throwable t = new HttpException(Response.error(400, ResponseBody.create(MediaType.parse(errorString), errorString)));
        final Error error = WealthsimpleGson.instance().fromJson(errorString, Error.class);
        when(mApi.getCandidate(command)).thenReturn(Observable.<Candidate>error(t));

        mPresenter.sendCommand(command);

        verify(mView).showError(error.toString());
        verifyNoMoreInteractions(mView);
    }

    @Test
    public void sendMessage_validMessage() throws Exception {
        final String command = "valid command";
        final Candidate candidate = new Candidate("6b681c89-65b6-4372-a8e7-288d26074394", "Bhaskar", "Polo");
        when(mApi.getCandidate(command)).thenReturn(Observable.just(candidate));

        mPresenter.sendCommand(command);

        verify(mView).showMessage(candidate.response);
        verifyNoMoreInteractions(mView);
    }
}