package com.hrenic.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.hrenic.popularmovies.util.Config;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents a movie retrieved from the Movie DB
 */
@Table(database = MovieDatabase.class)
public class Movie implements Parcelable {

    /*
        JSON keys
     */
    private static final String ID_KEY = "id";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String POSTER_URL_KEY = "poster_path";
    private static final String PLOT_SYNOPSIS_KEY = "overview";
    private static final String USER_RATING_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";

    @PrimaryKey
    @Column
    private int id;

    @Column
    private String originalTitle;

    @Column
    private String posterURL;

    @Column
    private String plotSynopsis;

    @Column
    private double userRating;

    @Column
    private String releaseDate;

    @Column(defaultValue = "0")
    private boolean favorite;

    public Movie() {
        // for DB
    }

    public Movie(JSONObject json) {
        try {
            id = json.getInt(ID_KEY);
            originalTitle = json.getString(ORIGINAL_TITLE_KEY);
            posterURL = json.getString(POSTER_URL_KEY);
            plotSynopsis = json.getString(PLOT_SYNOPSIS_KEY);
            userRating = json.getDouble(USER_RATING_KEY);
            releaseDate = json.getString(RELEASE_DATE_KEY);
            favorite = false;
        } catch (JSONException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private Movie(Parcel source) {
        id = source.readInt();
        originalTitle = source.readString();
        posterURL = source.readString();
        plotSynopsis = source.readString();
        userRating = source.readDouble();
        releaseDate = source.readString();
        favorite = source.readByte() != 0;
    }

    // PARCELABLE

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(posterURL);
        dest.writeString(plotSynopsis);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
        dest.writeByte(favorite ? (byte) 1 : 0);
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

    // GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getPlotSynopsis() {
        return plotSynopsis;
    }

    public void setPlotSynopsis(String plotSynopsis) {
        this.plotSynopsis = plotSynopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
