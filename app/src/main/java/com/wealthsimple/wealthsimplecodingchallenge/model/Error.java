package com.wealthsimple.wealthsimplecodingchallenge.model;

/**
 * Created by bhaskar on 2016-03-01
 */
public class Error {
    public String errorType;
    public String error;

    public Error(String type, String error) {
        this.errorType = type;
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", errorType, error);
    }
}
