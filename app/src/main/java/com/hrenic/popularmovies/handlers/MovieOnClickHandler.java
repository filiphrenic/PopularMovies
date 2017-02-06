package com.hrenic.popularmovies.handlers;

import com.hrenic.popularmovies.model.Movie;

/**
 * Handles click on movie in grid view
 */
public interface MovieOnClickHandler {
    void onClick(Movie movie);
}
