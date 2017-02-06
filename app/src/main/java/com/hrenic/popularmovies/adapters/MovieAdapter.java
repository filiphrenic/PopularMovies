package com.hrenic.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.handlers.MovieOnClickHandler;
import com.hrenic.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Movie adapter for home grid list
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private MovieOnClickHandler clickHandler;
    private List<Movie> movies;

    public MovieAdapter(Context context, MovieOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.setMovie(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    private void clickedOnPosition(int position) {
        clickHandler.onClick(movies.get(position));
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMovieTitleTextView;
        private ImageView mMoviePosterImageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            mMovieTitleTextView = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
        }

        void setMovie(Movie movie) {
            mMovieTitleTextView.setText(movie.getOriginalTitle());
            Picasso.with(context).load(movie.getFullPosterURL()).into(mMoviePosterImageView);
        }

        @Override
        public void onClick(View v) {
            clickedOnPosition(getAdapterPosition());
        }
    }
}
