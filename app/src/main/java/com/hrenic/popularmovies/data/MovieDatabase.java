package com.hrenic.popularmovies.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Database for storing movies
 */
@Database(name = MovieDatabase.DB_NAME, version = MovieDatabase.DB_VERSION)
public class MovieDatabase {
    static final String DB_NAME = "MovieDatabase";
    static final int DB_VERSION = 1;
}
