package com.wealthsimple.wealthsimplecodingchallenge.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.wealthsimple.wealthsimplecodingchallenge.model.Candidate;
import com.wealthsimple.wealthsimplecodingchallenge.model.Error;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleAPI;
import com.wealthsimple.wealthsimplecodingchallenge.service.WealthsimpleGson;
import com.wealthsimple.wealthsimplecodingchallenge.view.MainView;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Presenter for Main component.
 * Given a command, execute request against API and show message or error
 */
public class MainPresenter {
    private static final String TAG = "MainPresenter";
    private static final String RESPONSE_KEY = "response";
    private static final String ERROR_KEY = "error";

    @Inject
    MainView mView;

    @Inject
    WealthsimpleAPI mApi;

    private boolean mIsError = false; // default to no error

    private String mResponse;

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(RESPONSE_KEY, mResponse);
        savedInstanceState.putBoolean(ERROR_KEY, mIsError);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mResponse = savedInstanceState.getString(RESPONSE_KEY);
        mIsError = savedInstanceState.getBoolean(ERROR_KEY, false);
        showResponse();
    }

    /**
     * Send command to API, show message or error based on API response
     * @param command Command to send
     */
    public void sendCommand(String command) {
        if (TextUtils.isEmpty(command)) {
            mView.showError("Command required");
            return;
        }

        mApi.getCandidate(command)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Candidate>() {
                    @Override
                    public void call(Candidate candidate) {
                        if (candidate != null) {
                            mResponse = candidate.response;
                            mIsError = false;
                            showResponse();
                        } else {
                            Log.e(TAG, "Error parsing response");
                            mResponse = "Unable to parse response";
                            mIsError = true;
                            showResponse();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable t) {
                        Log.e(TAG, "Error fetching candidate", t);
                        mIsError = true;

                        try {
                            HttpException ex = (HttpException) t;
                            Error error = WealthsimpleGson.instance().fromJson(ex.response().errorBody().string(), Error.class);
                            mResponse = error != null ? error.toString() : t.getMessage();
                        } catch (ClassCastException | IOException e) {
                            mResponse = t.getMessage(); // show raw message if we couldn't figure out exception type
                        } finally {
                            showResponse();
                        }
                    }
                });
    }

    /**
     * Show response based on error state
     */
    private void showResponse() {
        if (mIsError) {
            mView.showError(mResponse);
        } else {
            mView.showMessage(mResponse);
        }
    }
}
