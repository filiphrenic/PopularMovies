package com.hrenic.popularmovies.util;

import android.net.Uri;
import android.util.Log;

import com.hrenic.popularmovies.BuildConfig;
import com.hrenic.popularmovies.model.SortCriteria;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * App config and constants
 */

public class Config {
    /*
        Used for building poster URLs
     */
    public static final String MOVIE_DB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String DEFAULT_IMAGE_SIZE = "w185";

    /*
        Base URL for API
     */
    public static final String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static final Map<String, String> API_PARAMS;

    /*
        Maps sort criteria to URL
     */
    private static final Map<SortCriteria, URL> URL_MAP;

    static {
        API_PARAMS = new HashMap<>();
        API_PARAMS.put("api_key", BuildConfig.MOVIE_DB_API_KEY);
        API_PARAMS.put("language", "en-US");
        API_PARAMS.put("page", "1");

        URL_MAP = new HashMap<>();
        for (SortCriteria criteria : SortCriteria.values()) {
            URL_MAP.put(criteria, buildAPIurl(criteria));
        }


    }

    /**
     * @param criteria sort criteria you want url for
     *
     * @return url or null if such doesn't exist
     */
    public static URL getFor(SortCriteria criteria) {
        return URL_MAP.get(criteria);
    }


    /**
     * Create API url for given sort order
     *
     * @param criteria can be either popular or top_rated
     *
     * @return built url
     */
    private static URL buildAPIurl(SortCriteria criteria) {

        Uri.Builder builder = Uri.parse(MOVIE_DB_API_BASE_URL + criteria).buildUpon();
        for (Map.Entry<String, String> entry : API_PARAMS.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }

        URL url;

        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException ex) {
            Log.v(TAG, "URL building for " + criteria + " failed");
            url = null;
        }

        return url;
    }

}
