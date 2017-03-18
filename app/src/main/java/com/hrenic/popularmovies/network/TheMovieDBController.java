package com.hrenic.popularmovies.network;

import android.support.annotation.NonNull;

import com.hrenic.popularmovies.BuildConfig;
import com.hrenic.popularmovies.data.Results;

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
public class TheMovieDBController<R> implements Callback<Results<R>> {

    private static final TheMovieDBAPI API = createAPI();

    private Function<TheMovieDBAPI, Call<Results<R>>> caller;
    private Consumer<List<R>> consumer;

    private Consumer<Void> onCall;
    private Consumer<Void> onSuccess;
    private Consumer<Throwable> onError;

    public TheMovieDBController(
            @NonNull Function<TheMovieDBAPI, Call<Results<R>>> caller,
            @NonNull Consumer<List<R>> consumer,
            Consumer<Void> onCall,
            Consumer<Void> onSuccess,
            Consumer<Throwable> onError
    ) {
        this.caller = caller;
        this.consumer = consumer;
        this.onCall = onCall;
        this.onSuccess = onSuccess;
        this.onError = onError;
    }

    public TheMovieDBController(
            Function<TheMovieDBAPI, Call<Results<R>>> caller,
            Consumer<List<R>> consumer
    ) {
        this(caller, consumer, null, null, null);
    }

    public void getResults() {
        if (onCall != null) {
            onCall.accept(null);
        }
        Call<Results<R>> call = caller.apply(API);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Results<R>> call, Response<Results<R>> response) {
        if (response.isSuccessful()) {
            if (onSuccess != null) {
                onSuccess.accept(null);
            }
            consumer.accept(response.body().results);
        } else {
            onFailure(call, new Exception("Request failed"));
        }
    }

    @Override
    public void onFailure(Call<Results<R>> call, Throwable t) {
        if (onError != null) {
            onError.accept(t);
        }
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
