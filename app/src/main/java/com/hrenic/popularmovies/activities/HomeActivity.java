package com.hrenic.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hrenic.popularmovies.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    /**
     * Sort movies by most popular first
     */
    private void sortByMostPopular() {
        // TODO
    }

    /**
     * Sort movies by top rated first
     */
    private void sortByTopRated() {
        // TODO
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
                        HomeActivity.this.sortByMostPopular();
                        break;

                    case R.id.sort_by_top_rated:
                        HomeActivity.this.sortByTopRated();
                        break;

                    default:
                        return false;

                }
                return true;
            }

        });
    }

}
