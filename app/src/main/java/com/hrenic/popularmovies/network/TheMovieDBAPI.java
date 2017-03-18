package com.hrenic.popularmovies.network;

import com.hrenic.popularmovies.data.Movie;
import com.hrenic.popularmovies.data.Results;
import com.hrenic.popularmovies.data.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * API for movie DB
 */
public interface TheMovieDBAPI {

    String ENDPOINT = "https://api.themoviedb.org/3/movie/";

    @GET("popular")
    Call<Results<Movie>> getMostPopular();

    @GET("top_rated")
    Call<Results<Movie>> getTopRated();

    @GET("{id}/videos")
    Call<Results<Video>> getVideos(@Path("id") int movieID);

    @GET("{id}/reviews")
    Call<Results<Video>> getReviews(@Path("id") int movieID);

}
