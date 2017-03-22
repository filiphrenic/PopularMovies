package com.hrenic.popularmovies.data;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;

/**
 * Review retrieved from TheMovieDB
 */
@Table(database = MovieDatabase.class)
@Parcel
public class Review extends BaseModel {

    /*
        JSON KEYS
     */
    private static final String ID_KEY = "id";
    private static final String AUTHOR_KEY = "author";
    private static final String CONTENT_KEY = "content";
    private static final String URL_KEY = "url";

    @PrimaryKey
    @Column
    @SerializedName(ID_KEY)
    private String id;

    @Column
    @SerializedName(AUTHOR_KEY)
    private String author;

    @Column
    @SerializedName(CONTENT_KEY)
    private String content;

    @Column
    @SerializedName(URL_KEY)
    private String url;

    @ForeignKey(stubbedRelationship = true)
    private Movie movie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
