package com.hrenic.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Video retrieved from TheMovieDB
 */
@Parcel
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
    private String id;

    @SerializedName(KEY_KEY)
    private String key;

    @SerializedName(NAME_KEY)
    private String name;

    @SerializedName(SITE_KEY)
    private String site;

    @SerializedName(SIZE_KEY)
    private int size;

    @SerializedName(TYPE_KEY)
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
