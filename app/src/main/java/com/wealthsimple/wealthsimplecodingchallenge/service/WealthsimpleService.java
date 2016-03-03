package com.wealthsimple.wealthsimplecodingchallenge.service;

import com.google.gson.JsonObject;
import com.wealthsimple.wealthsimplecodingchallenge.model.Candidate;

import retrofit2.http.Body;
import retrofit2.http.PUT;
import rx.Observable;

/**
 * Created by bhaskar on 2016-03-01
 */
public interface WealthsimpleService {
    /**
     * Get a candidate response based on command
     * @param candidate Candidate request body
     * @return Candidate response
     */
    @PUT("candidates")
    Observable<Candidate> getCandidate(@Body JsonObject candidate);
}
