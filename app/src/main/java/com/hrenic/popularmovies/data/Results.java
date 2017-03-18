package com.hrenic.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Used when MovieDB sends a json object with a 'results' list
 */
public class Results<T> {

    private static final String RESULTS_KEY = "results";

    @SerializedName(RESULTS_KEY)
    public List<T> results;
}
