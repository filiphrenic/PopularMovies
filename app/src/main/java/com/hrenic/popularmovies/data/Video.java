package com.hrenic.popularmovies.data;

import com.google.gson.annotations.SerializedName;

/**
 * Video retrieved from TheMovieDB
 */
public class Video {

    /*
        JSON KEYS
     */
    private static final String ID_KEY = "id";
    private static final String KEY_KEY = "key";
    private static final String NAME_KEY = "name";
    private static final String SITE_KEY = "site";
    private static final String SIZE_KEY = "size";
    private static final String TYPE_KEY = "type";

    @SerializedName(ID_KEY)
    private int id;

    @SerializedName(KEY_KEY)
    private String key;

    @SerializedName(NAME_KEY)
    private String name;

    @SerializedName(SITE_KEY)
    private String site;

    @SerializedName(SIZE_KEY)
    private String size;

    @SerializedName(TYPE_KEY)
    private double type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getType() {
        return type;
    }

    public void setType(double type) {
        this.type = type;
    }
}
