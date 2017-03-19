package com.hrenic.popularmovies.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.data.Movie;
import com.hrenic.popularmovies.databinding.ActivityMovieBinding;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        ActivityMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        Movie movie = intent.getParcelableExtra(MOVIE_KEY);

        // TODO load videos and reviews

        setTitle(movie.getOriginalTitle());

        Picasso.with(this).load(movie.getFullPosterURL()).into(binding.moviePosterDetail);
        binding.movieReleaseDateDetail.setText(movie.getReleaseDate());
        binding.movieRatingDetail.setText(String.format(Locale.getDefault(), "%.1f", movie.getUserRating()));
        binding.movieSynopsisDetail.setText(movie.getPlotSynopsis());

        binding.moviePosterDetail.setMinimumHeight(binding.moviePosterDetail.getWidth());

    }
}
