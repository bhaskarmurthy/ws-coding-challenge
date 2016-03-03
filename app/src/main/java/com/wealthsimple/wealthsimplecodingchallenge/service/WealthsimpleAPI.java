package com.wealthsimple.wealthsimplecodingchallenge.service;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wealthsimple.wealthsimplecodingchallenge.model.Candidate;

import java.util.Map;

import rx.Observable;

/**
 * Created by bhaskar on 2016-03-01
 */
public class WealthsimpleAPI {

    private static final String TAG = "WealthsimpleAPI";

    private final WealthsimpleService mService;
    private final Gson mGson;
    private final String mKey;

    public WealthsimpleAPI(WealthsimpleService service, Gson gson, String key) {
        mService = service;
        mGson = gson;
        mKey = key;
    }

    public Observable<Candidate> getCandidate(final String command) {
        /*
        return Observable.create(new Observable.OnSubscribe<Candidate>() {
            @Override
            public void call(Subscriber<? super Candidate> subscriber) {
                String body = String.format("{ \"candidate\" : { \"key\": \"%s\", \"command\": \"%s\" } }", mKey, command);
                Request request = new Request.Builder()
                        .addHeader("Content-Type", "application/json")
                        .method("GET", RequestBody.create(MediaType.parse(body), body))
                        .build();
                try {
                    Response response = mHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {
                        subscriber.onNext(mGson.fromJson(response.body().string(), Candidate.class));
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(mGson.fromJson(response.body().string(), Error.class));
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error fetching candidate for command: " + command, e);
                    subscriber.onError(e);
                }
            }
        });
        */

        JsonObject candidate = new JsonObject();
        candidate.addProperty("key", mKey);
        candidate.addProperty("command", command);
        JsonObject body = new JsonObject();
        body.add("candidate", candidate);
        return mService.getCandidate(body);
    }
}
