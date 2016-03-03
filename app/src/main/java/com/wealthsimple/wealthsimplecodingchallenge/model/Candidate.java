package com.wealthsimple.wealthsimplecodingchallenge.model;

/**
 * Represents a Candidate.
 */
public class Candidate {
    public String key;
    public String name;
    public String response;

    /**
     * Constructor
     *
     * @param key Unique key for candidate
     * @param name Candidate's name
     * @param response Candidate's response based on command issued
     */
    public Candidate(String key, String name, String response) {
        this.key = key;
        this.name = name;
        this.response = response;
    }
}
