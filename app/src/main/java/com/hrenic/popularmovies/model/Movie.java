package com.hrenic.popularmovies.model;

import com.hrenic.popularmovies.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents a movie retrieved from the Movie DB
 */

public class Movie {

    /*
        JSON keys
     */

    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String POSTER_URL_KEY = "poster_path";
    private static final String PLOT_SYNOPSIS_KEY = "overview";
    private static final String USER_RATING_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";

    private String originalTitle;
    private String posterURL;
    private String plotSynopsis;
    private double userRating;
    private String releaseDate;

    public Movie(JSONObject json) {
        try {
            originalTitle = json.getString(ORIGINAL_TITLE_KEY);
            posterURL = json.getString(POSTER_URL_KEY);
            plotSynopsis = json.getString(PLOT_SYNOPSIS_KEY);
            userRating = json.getDouble(USER_RATING_KEY);
            releaseDate = json.getString(RELEASE_DATE_KEY);
        } catch (JSONException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public String getFullPosterURL() {
        return Config.MOVIE_DB_POSTER_BASE_URL + Config.DEFAULT_IMAGE_SIZE + posterURL;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
