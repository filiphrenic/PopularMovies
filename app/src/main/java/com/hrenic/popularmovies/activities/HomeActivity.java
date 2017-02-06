package com.hrenic.popularmovies.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.model.Movie;
import com.hrenic.popularmovies.model.SortCriteria;
import com.hrenic.popularmovies.util.Config;
import com.hrenic.popularmovies.util.NetworkUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private SortCriteria currentCriteria;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        currentCriteria = SortCriteria.MOST_POPULAR;
        movies = new LinkedList<>(); // XXX
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /**
     * Sort movies by given criteria
     */
    private void sortBy(SortCriteria criteria) {
        if (currentCriteria == criteria) {
            return;
        }

        if (!NetworkUtility.isOnline(this)) {
            showNoNetworkConnection();
            return;
        }

        currentCriteria = criteria;
        new FetchMoviesTask().execute(criteria);
    }

    private void showNoNetworkConnection() {
        Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show();
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

    private class FetchMoviesTask extends AsyncTask<SortCriteria, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // TODO enable spinner
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
            movies.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    Movie movie = new Movie(jsonArray.getJSONObject(i));
                    movies.add(movie);
                } catch (JSONException ex) {
                    Log.v(TAG, "Error while extracting movie", ex);
                }
            }
            // TODO disable spinner
            // TODO populate grid list
        }
    }


}
