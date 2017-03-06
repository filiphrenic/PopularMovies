package com.hrenic.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.Locale;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        if (intent == null) {
            return;
        }

        ImageView mPosterImageView = (ImageView) findViewById(R.id.iv_movie_poster_detail);
        TextView mReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_release_date_detail);
        TextView mRatingTextView = (TextView) findViewById(R.id.tv_movie_rating_detail);
        TextView mSynopsisTextView = (TextView) findViewById(R.id.tv_movie_synopsis_detail);

        Movie movie = intent.getParcelableExtra(MOVIE_KEY);
        setTitle(movie.getOriginalTitle());

        Picasso.with(this).load(movie.getFullPosterURL()).into(mPosterImageView);
        mReleaseDateTextView.setText(movie.getReleaseDate());
        mRatingTextView.setText(String.format(Locale.getDefault(), "%.1f", movie.getUserRating()));
        mSynopsisTextView.setText(movie.getPlotSynopsis());

        mPosterImageView.setMinimumHeight(mPosterImageView.getWidth());

    }
}
