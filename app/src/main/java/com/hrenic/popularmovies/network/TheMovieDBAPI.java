package com.hrenic.popularmovies.network;

import com.hrenic.popularmovies.data.Movie;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * API for movie DB
 */

public interface TheMovieDBAPI {

    String ENDPOINT = "https://api.themoviedb.org/3/movie/";

    @GET("popular")
    Call<Movie.MovieResults> getMostPopular();

    @GET("top_rated")
    Call<Movie.MovieResults> getTopRated();

}
