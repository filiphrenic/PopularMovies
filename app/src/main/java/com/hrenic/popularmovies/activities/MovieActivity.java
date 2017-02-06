package com.hrenic.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.model.Movie;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        if (intent != null) {
            Movie movie = intent.getParcelableExtra(MOVIE_KEY);
            setTitle(movie.getOriginalTitle());
        }
    }
}
