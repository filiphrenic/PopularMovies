package com.hrenic.popularmovies.network;

import com.hrenic.popularmovies.BuildConfig;
import com.hrenic.popularmovies.data.Movie;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Abstract controller for getting movies from endpoint
 */
public class TheMovieDBController implements Callback<Movie.MovieResults> {

    private static final TheMovieDBAPI API = createAPI();

    private Function<TheMovieDBAPI, Call<Movie.MovieResults>> caller;
    private Consumer<List<Movie>> consumer;
    private Consumer<Throwable> onError;

    public TheMovieDBController(
            Function<TheMovieDBAPI, Call<Movie.MovieResults>> caller,
            Consumer<List<Movie>> consumer,
            Consumer<Throwable> onError
    ) {
        this.caller = caller;
        this.consumer = consumer;
        this.onError = onError;
    }

    public void getMovies() {
        Call<Movie.MovieResults> call = caller.apply(API);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Movie.MovieResults> call, Response<Movie.MovieResults> response) {
        if (response.isSuccessful()) {
            consumer.accept(response.body().results);
        } else {
            onFailure(call, new Exception("Request failed"));
        }
    }

    @Override
    public void onFailure(Call<Movie.MovieResults> call, Throwable t) {
        onError.accept(t);
    }

    /**
     * Create API endpoint
     *
     * @return API
     */
    private static TheMovieDBAPI createAPI() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request request = chain.request();
                            HttpUrl url = request.url().newBuilder()
                                    .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                                    .addQueryParameter("language", "en-US")
                                    .addQueryParameter("page", "1")
                                    .build();
                            request = request.newBuilder().url(url).build();
                            return chain.proceed(request);
                        }
                ).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TheMovieDBAPI.ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(TheMovieDBAPI.class);
    }
}
