package com.hrenic.popularmovies.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.adapters.MovieAdapter;
import com.hrenic.popularmovies.data.Movie;
import com.hrenic.popularmovies.model.SortCriteria;
import com.hrenic.popularmovies.util.Config;
import com.hrenic.popularmovies.util.Initializer;
import com.hrenic.popularmovies.util.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements
        LoaderManager.LoaderCallbacks<Cursor>,
        MovieAdapter.MovieOnClickHandler {

    private static final String TAG = "HomeActivity";

    private SortCriteria currentCriteria;
    private MovieAdapter mAdapter;

    private RecyclerView mMoviewRecyclerView;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Initializer.init(this.getApplication());

        setContentView(R.layout.activity_home);

        mMoviewRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorTextView = (TextView) findViewById(R.id.tv_error_message);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mAdapter = new MovieAdapter(this, this);

        mMoviewRecyclerView.setLayoutManager(manager);
        mMoviewRecyclerView.setAdapter(mAdapter);

        sortBy(SortCriteria.MOST_POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    private void showMovieData() {
        mErrorTextView.setVisibility(View.INVISIBLE);
        mMoviewRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String msg) {
        mErrorTextView.setVisibility(View.VISIBLE);
        mErrorTextView.setText(msg);
        mMoviewRecyclerView.setVisibility(View.INVISIBLE);
    }

    /**
     * Sort movies by given criteria
     */
    private void sortBy(SortCriteria criteria) {
        if (currentCriteria == criteria) {
            return;
        }

        if (!NetworkUtility.isOnline(this)) {
            showErrorMessage("No internet connection");
            return;
        }

        currentCriteria = criteria;
        new FetchMoviesTask().execute(criteria);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(MovieActivity.MOVIE_KEY, movie);
        startActivity(intent);
    }

    /**
     * Show popup menu for sort criteria
     *
     * @param item item that was clicked
     */
    public void showSortPopup(MenuItem item) {

        View sortItem = findViewById(R.id.action_sort);
        PopupMenu popup = new PopupMenu(this, sortItem);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.sort_popup, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.sort_by_most_popular:
                        HomeActivity.this.sortBy(SortCriteria.MOST_POPULAR);
                        break;

                    case R.id.sort_by_top_rated:
                        HomeActivity.this.sortBy(SortCriteria.TOP_RATED);
                        break;

                    default:
                        return false;

                }
                return true;
            }

        });
    }

    // LOADER

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class FetchMoviesTask extends AsyncTask<SortCriteria, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected JSONArray doInBackground(SortCriteria... params) {

            if (params == null || params.length < 1) {
                return null;
            }

            JSONArray result = null;

            try {
                String json = NetworkUtility.getContentFromURL(Config.getFor(params[0]));
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("results")) {
                    result = jsonObject.getJSONArray("results");
                }

            } catch (IOException | JSONException ex) {
                Log.v(TAG, "Error while getting movies json", ex);
            }

            return result;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            if (jsonArray == null) {
                showErrorMessage("Error while loading movie data");
                return;
            }

            showMovieData();

            List<Movie> movies = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Movie movie = new Movie(jsonArray.getJSONObject(i));
                    movies.add(movie);
                } catch (JSONException ex) {
                    Log.v(TAG, "Error while extracting movie", ex);
                }
            }

            mProgressBar.setVisibility(View.INVISIBLE);
            mAdapter.setMovies(movies);
        }
    }

}
