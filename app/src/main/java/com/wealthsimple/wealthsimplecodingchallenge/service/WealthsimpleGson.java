package com.wealthsimple.wealthsimplecodingchallenge.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.wealthsimple.wealthsimplecodingchallenge.model.Candidate;
import com.wealthsimple.wealthsimplecodingchallenge.model.Error;

import java.lang.reflect.Type;

/**
 * Created by bhaskar on 2016-03-02
 */
public class WealthsimpleGson {

    private static volatile Gson sInstance;

    private WealthsimpleGson() {}

    public static Gson instance() {
        if (sInstance == null) {
            synchronized (WealthsimpleGson.class) {
                if (sInstance == null) {
                    sInstance = new GsonBuilder()
                            .registerTypeAdapter(Candidate.class, new JsonDeserializer<Candidate>() {
                                @Override
                                public Candidate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                    try {
                                        final JsonObject jsonObject = json.getAsJsonObject().has("candidate") ? json.getAsJsonObject().getAsJsonObject("candidate") : null;
                                        if (jsonObject != null) {
                                            return new Candidate(jsonObject.get("key").getAsString(), jsonObject.get("name").getAsString(), jsonObject.get("response").getAsString());
                                        } else {
                                            return null;
                                        }
                                    } catch (IllegalStateException e) {
                                        return null;
                                    }
                                }
                            })
                            .registerTypeAdapter(Error.class, new JsonDeserializer<Error>() {
                                @Override
                                public Error deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                    try {
                                        final JsonObject jsonObject = json.getAsJsonObject();
                                        return new Error(jsonObject.get("error_type").getAsString(), jsonObject.get("error").getAsString());
                                    } catch (IllegalStateException e) {
                                        return null;
                                    }
                                }
                            })
                            .create();
                }
            }
        }
        return sInstance;
    }
}
