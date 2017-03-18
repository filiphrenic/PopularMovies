package com.hrenic.popularmovies.data;

/**
 * Review retrieved from TheMovieDB
 */
public class Review {

    /*
        JSON KEYS
     */
    private static final String ID_KEY = "id";
    private static final String AUTHOR_KEY = "author";
    private static final String CONTENT = "content";
    private static final String URL_KEY = "url";

    private String id;

    private String author;

    private String content;

    private String url;

    public static String getIdKey() {
        return ID_KEY;
    }

    public static String getAuthorKey() {
        return AUTHOR_KEY;
    }

    public static String getCONTENT() {
        return CONTENT;
    }

    public static String getUrlKey() {
        return URL_KEY;
    }

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
