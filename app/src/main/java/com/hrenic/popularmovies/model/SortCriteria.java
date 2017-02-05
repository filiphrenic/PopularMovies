package com.hrenic.popularmovies.model;

/**
 * Movie sort criteria
 */

public enum SortCriteria {
    MOST_POPULAR, TOP_RATED;

    /**
     * @return part of url that corresponds to given criteria
     */
    @Override
    public String toString() {
        switch (this) {
            case MOST_POPULAR:
                return "popular";
            case TOP_RATED:
                return "top_rated";
            default:
                return "";
        }
    }
}
