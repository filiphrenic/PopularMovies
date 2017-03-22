package com.hrenic.popularmovies.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
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

public class MovieActivity extends AppCompatActivity implements VideoAdapter.VideOnClickHandler {

    public static final String MOVIE_KEY = "MovieActivity.Movie.key";
    Movie movie;

    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        try {
            if (intent != null) {
                movie = Parcels.unwrap(intent.getParcelableExtra(MOVIE_KEY));
            } else {
                movie = Parcels.unwrap(savedInstanceState.getParcelable(MOVIE_KEY));
            }
        } catch (Exception ignore) {
        }
        if (movie == null) {
            Toast.makeText(this, "No movie found :(", Toast.LENGTH_LONG).show();
            return;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movie != null) {
            outState.putParcelable(MOVIE_KEY, Parcels.wrap(movie));
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_favorite);
        if (movie != null) {
            item.setIcon(getHeart());
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (movie.getVideos() == null || movie.getVideos().size() == 0) {
                    Toast.makeText(this, "No trailers to share", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(createShareIntent(movie.getVideos().get(0).getKey()));
                }
                break;
            case R.id.action_favorite:
                movie.setFavorite(!movie.isFavorite());

                // save movie
                if (movie.exists()) {
                    movie.update();
                } else {
                    movie.save();
                }

                // update heart icon
                item.setIcon(getHeart());

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
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
    public void onClick(Video video, boolean share) {
        String videoID = video.getKey();
        if (share) {
            startActivity(createShareIntent(videoID));
        } else {
            if (video.isYoutubeVideo()) {
                watchYoutubeVideo(videoID);
            } else {
                Toast.makeText(this, "Don't know how to open video on site " + video.getSite(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void watchYoutubeVideo(String videoID) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoID)));
        } catch (ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink(videoID))));
        }
    }

    private Intent createShareIntent(String videoID) {
        String content = "Watch " + movie.getOriginalTitle() + " " +
                youtubeLink(videoID) + " " + getString(R.string.hash_tag);

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        return shareIntent;
    }

    private static String youtubeLink(String videoID) {
        return "https://youtu.be/" + videoID;
    }

    private Drawable getHeart() {
        int heartID = movie.isFavorite() ? R.drawable.heart_on : R.drawable.heart_off;
        return ContextCompat.getDrawable(this, heartID);
    }
}
