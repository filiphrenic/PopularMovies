package com.hrenic.popularmovies.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.adapters.ReviewAdapter;
import com.hrenic.popularmovies.adapters.VideoAdapter;
import com.hrenic.popularmovies.data.Movie;
import com.hrenic.popularmovies.data.Review;
import com.hrenic.popularmovies.data.Video;
import com.hrenic.popularmovies.databinding.ActivityMovieBinding;
import com.hrenic.popularmovies.network.TheMovieDBController;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;
import java.util.Locale;

public class MovieActivity extends AppCompatActivity
        implements VideoAdapter.VideOnClickHandler {

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";

    Movie movie;

    private VideoAdapter mVideoAdapter;

    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent != null) {
            movie = Parcels.unwrap(intent.getParcelableExtra(MOVIE_KEY));
        }
        if (movie == null) {
            Toast.makeText(this, "No movie found :(", Toast.LENGTH_LONG).show();
            return;
        }

        ActivityMovieBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        setTitle(movie.getOriginalTitle());
        binding.movieTitleDetail.setText(movie.getOriginalTitle());

        Picasso.with(this).load(movie.getFullPosterURL()).into(binding.moviePosterDetail);
        binding.movieReleaseDateDetail.setText(movie.getReleaseDate());
        binding.movieRatingDetail.setText(String.format(Locale.getDefault(), "%.1f/10.0", movie.getUserRating()));
        binding.moviePlotSynopsisDetail.setText(movie.getPlotSynopsis());
        binding.moviePosterDetail.setMinimumHeight(binding.moviePosterDetail.getWidth());

        mVideoAdapter = new VideoAdapter(this, this);
        binding.trailersDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.trailersDetail.setHasFixedSize(true);
        binding.trailersDetail.setAdapter(mVideoAdapter);
        mVideoAdapter.setVideos(movie.getVideos());

        mReviewAdapter = new ReviewAdapter(this);
        binding.reviewsDetail.setLayoutManager(new LinearLayoutManager(this));
        binding.reviewsDetail.setHasFixedSize(true);
        binding.reviewsDetail.setAdapter(mReviewAdapter);
        mReviewAdapter.setReviews(movie.getReviews());

        // load videos and reviews
        if (movie.getVideos() == null || movie.getVideos().size() == 0) {
            new TheMovieDBController<>(api -> api.getVideos(movie.getId()), this::setVideos).getResults();
        }
        if (movie.getReviews() == null || movie.getReviews().size() == 0) {
            new TheMovieDBController<>(api -> api.getReviews(movie.getId()), this::setReviews).getResults();
        }

    }

    private void setVideos(List<Video> videos) {
        if (videos == null) {
            return;
        }
        movie.setVideos(videos);
        mVideoAdapter.setVideos(videos);
    }

    private void setReviews(List<Review> reviews) {
        if (reviews == null) {
            return;
        }
        movie.setReviews(reviews);
        mReviewAdapter.setReviews(reviews);
    }

    @Override
    public void onClick(Video video) {
        if (video.isYoutubeVideo()) {
            watchYoutubeVideo(video.getKey());
        } else {
            Toast.makeText(this, "Don't know how to open video on site " + video.getSite(), Toast.LENGTH_LONG).show();
        }
    }

    private void watchYoutubeVideo(String id) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id)));
        } catch (ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id)));
        }
    }
}
