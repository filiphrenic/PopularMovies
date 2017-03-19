package com.hrenic.popularmovies.activities;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.adapters.MovieAdapter;
import com.hrenic.popularmovies.data.Movie;
import com.hrenic.popularmovies.data.Results;
import com.hrenic.popularmovies.databinding.ActivityHomeBinding;
import com.hrenic.popularmovies.model.SortCriteria;
import com.hrenic.popularmovies.network.NetworkUtility;
import com.hrenic.popularmovies.network.TheMovieDBAPI;
import com.hrenic.popularmovies.network.TheMovieDBController;
import com.hrenic.popularmovies.util.Initializer;

import java.util.List;
import java.util.function.Function;

import retrofit2.Call;

public class HomeActivity extends AppCompatActivity
        implements
        LoaderManager.LoaderCallbacks<Cursor>,
        MovieAdapter.MovieOnClickHandler {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private SortCriteria currentCriteria;
    private MovieAdapter mAdapter;

    /*
        CONTROLLERS
     */

    private TheMovieDBController<Movie> createController(Function<TheMovieDBAPI, Call<Results<Movie>>> caller) {
        return new TheMovieDBController<>(
                caller,
                this::setMovies,
                __ -> showLoading(),
                __ -> showMovieData(),
                t -> showErrorMessage(t.getMessage())
        );
    }

    private TheMovieDBController<Movie> mControllerMostPopular
            = createController(TheMovieDBAPI::getMostPopular);

    private TheMovieDBController<Movie> mControllerTopRated
            = createController(TheMovieDBAPI::getTopRated);

    private ActivityHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Initializer.init(this.getApplication());

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mAdapter = new MovieAdapter(this, this);

        mBinding.moviesGrid.setLayoutManager(manager);
        mBinding.moviesGrid.setAdapter(mAdapter);

        sortBy(SortCriteria.MOST_POPULAR);
    }

    private void setMovies(List<Movie> movies) {
        mAdapter.setMovies(movies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    private void showLoading() {
        mBinding.loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showMovieData() {
        mBinding.loadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.errorMessage.setVisibility(View.INVISIBLE);
        mBinding.moviesGrid.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String msg) {
        mBinding.loadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.errorMessage.setVisibility(View.VISIBLE);
        mBinding.errorMessage.setText(msg);
        mBinding.moviesGrid.setVisibility(View.INVISIBLE);
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

        TheMovieDBController<Movie> controller =
                criteria == SortCriteria.MOST_POPULAR
                        ? mControllerMostPopular
                        : mControllerTopRated;
        controller.getResults();
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

        popup.setOnMenuItemClickListener(item1 -> {

            switch (item1.getItemId()) {

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

}
