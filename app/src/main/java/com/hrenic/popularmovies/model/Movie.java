package com.hrenic.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.hrenic.popularmovies.util.Config;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents a movie retrieved from the Movie DB
 */

public class Movie implements Parcelable {

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

    private Movie(Parcel source) {
        originalTitle = source.readString();
        posterURL = source.readString();
        plotSynopsis = source.readString();
        userRating = source.readDouble();
        releaseDate = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(posterURL);
        dest.writeString(plotSynopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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
