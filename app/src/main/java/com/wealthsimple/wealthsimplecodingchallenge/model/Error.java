package com.wealthsimple.wealthsimplecodingchallenge.model;

/**
 * Error from server
 */
public class Error {
    public String errorType;
    public String error;

    /**
     * Constructor
     *
     * @param type Type of error
     * @param error Friendly error message
     */
    public Error(String type, String error) {
        this.errorType = type;
        this.error = error;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", errorType, error);
    }
}
