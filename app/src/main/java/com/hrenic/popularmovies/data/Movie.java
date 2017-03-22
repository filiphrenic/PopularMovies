package com.hrenic.popularmovies.data;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.provider.BaseSyncableProviderModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

/**
 * This class represents a movie retrieved from the Movie DB
 */
@Table(database = MovieDatabase.class)
@Parcel
public class Movie extends BaseSyncableProviderModel {

    /*
        Used for building poster URLs
     */
    private static final String MOVIE_DB_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String DEFAULT_IMAGE_SIZE = "w185";

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
    @SerializedName(ID_KEY)
    private int id;

    @Column
    @SerializedName(ORIGINAL_TITLE_KEY)
    private String originalTitle;

    @Column
    @SerializedName(POSTER_URL_KEY)
    private String posterURL;

    @Column
    @SerializedName(PLOT_SYNOPSIS_KEY)
    private String plotSynopsis;

    @Column
    @SerializedName(USER_RATING_KEY)
    private double userRating;

    @Column
    @SerializedName(RELEASE_DATE_KEY)
    private String releaseDate;

    @Column(defaultValue = "0")
    private boolean favorite;

    List<Video> videos;

    List<Review> reviews;

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

    public String getFullPosterURL() {
        return MOVIE_DB_POSTER_BASE_URL + DEFAULT_IMAGE_SIZE + posterURL;
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


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "videos")
    public List<Video> getVideos() {
        if (videos == null) {
            videos = SQLite.select()
                    .from(Video.class)
                    .where(Video_Table.movie_id.is(id))
                    .queryList();
        }
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        videos.forEach(v -> v.setMovie(this));
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "reviews")
    public List<Review> getReviews() {
        if (reviews == null) {
            reviews = SQLite.select()
                    .from(Review.class)
                    .where(Review_Table.movie_id.is(id))
                    .queryList();
        }
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        reviews.forEach(r -> r.setMovie(this));
    }

    @Override
    public Uri getDeleteUri() {
        return MovieProvider.MovieProviderModel.withID(id);
    }

    @Override
    public Uri getInsertUri() {
        return MovieProvider.MovieProviderModel.withID(id);
    }

    @Override
    public Uri getUpdateUri() {
        return MovieProvider.MovieProviderModel.withID(id);
    }

    @Override
    public Uri getQueryUri() {
        return MovieProvider.MovieProviderModel.withID(id);
    }
}
