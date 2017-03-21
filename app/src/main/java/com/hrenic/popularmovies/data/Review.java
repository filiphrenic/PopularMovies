package com.hrenic.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Review retrieved from TheMovieDB
 */
@Parcel
public class Review {

    /*
        JSON KEYS
     */
    private static final String ID_KEY = "id";
    private static final String AUTHOR_KEY = "author";
    private static final String CONTENT_KEY = "content";
    private static final String URL_KEY = "url";

    @SerializedName(ID_KEY)
    private String id;

    @SerializedName(AUTHOR_KEY)
    private String author;

    @SerializedName(CONTENT_KEY)
    private String content;

    @SerializedName(URL_KEY)
    private String url;

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
}
