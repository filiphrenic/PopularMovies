package com.hrenic.popularmovies.util;

import android.net.Uri;
import android.util.Log;

import com.hrenic.popularmovies.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

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
    private static final String MOVIE_DB_API_BASE_URL = "https://api.themoviedb.org/3/movie/";

    private static final String SORT_ORDER_POPULAR = "popular";
    private static final String SORT_ORDER_TOP_RATED = "top_rated";

    private static final String API_KEY = BuildConfig.MOVIE_DB_API_KEY;
    private static final String LANGUAGE = "en-US";
    private static final String PAGE = "1";

    private static final String API_KEY_PARAM = "api-key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGE_PARAM = "page";

    public static final URL POPULAR_MOVIES = buildAPIurl(SORT_ORDER_POPULAR);
    public static final URL TOP_RATED_MOVIES = buildAPIurl(SORT_ORDER_TOP_RATED);

    /**
     * Create API url for given sort order
     *
     * @param sortOrder can be either popular or top_rated
     *
     * @return built url
     */
    private static URL buildAPIurl(String sortOrder) {

        Uri builtUri = Uri.parse(MOVIE_DB_API_BASE_URL + sortOrder).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, LANGUAGE)
                .appendQueryParameter(PAGE_PARAM, PAGE)
                .build();

        URL url;

        try {
            url = new URL(builtUri.toString());
            Log.v(TAG, "Built URL for sort order " + sortOrder + " " + url);
        } catch (MalformedURLException ex) {
            Log.v(TAG, "URL building for " + sortOrder + " failed");
            url = null;
        }

        return url;
    }

}
