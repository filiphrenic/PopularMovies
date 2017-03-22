package com.hrenic.popularmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Movie adapter for home grid list
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    /**
     * Handles click on movie in grid view
     */
    public interface MovieOnClickHandler {
        void onClick(Movie movie);
    }

    private Context mContext;
    private MovieOnClickHandler mOnClickHandler;
    private Cursor mCursor;
    private List<Movie> movies;

    public MovieAdapter(@NonNull Context context, MovieOnClickHandler onClickHandler) {
        this.mContext = context;
        this.mOnClickHandler = onClickHandler;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void swapCursor(Cursor newCursor) {
        if (newCursor != null) {
            mCursor = newCursor;
            notifyDataSetChanged();
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.movie_grid_item, parent, false);
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

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mMovieTitleTextView;
        private ImageView mMoviePosterImageView;
        private ImageView mFavoriteButtonImageView;

        MovieViewHolder(View itemView) {
            super(itemView);
            mMovieTitleTextView = (TextView) itemView.findViewById(R.id.tv_movie_title);
            mMoviePosterImageView = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            mFavoriteButtonImageView = (ImageView) itemView.findViewById(R.id.iv_fav_button);
            itemView.setOnClickListener(this);
        }

        void setMovie(Movie movie) {
            mMovieTitleTextView.setText(movie.getOriginalTitle());
            Picasso.with(mContext).load(movie.getFullPosterURL()).into(mMoviePosterImageView);

            int heartID = movie.isFavorite() ? R.drawable.heart_on : R.drawable.heart_off;
            Drawable heart = ContextCompat.getDrawable(mContext, heartID);
            mFavoriteButtonImageView.setImageDrawable(heart);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickHandler != null) {
                mOnClickHandler.onClick(movies.get(getAdapterPosition()));
            }
        }
    }
}
