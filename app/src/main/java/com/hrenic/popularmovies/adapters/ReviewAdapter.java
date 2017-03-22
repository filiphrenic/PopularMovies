package com.hrenic.popularmovies.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.data.Review;

import java.util.List;

/**
 * Adapter for reviews
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    private Context mContext;
    private List<Review> reviews;

    public ReviewAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.setReview(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    public void setReviews(List<Review> reviews) {
        if (reviews == null) {
            return;
        }
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView mUsernameTextView;
        private TextView mContentTextView;

        ReviewViewHolder(View itemView) {
            super(itemView);

            mUsernameTextView = (TextView) itemView.findViewById(R.id.review_author);
            mContentTextView = (TextView) itemView.findViewById(R.id.review_content);
        }

        void setReview(Review review) {
            mUsernameTextView.setText(review.getAuthor());
            mContentTextView.setText(review.getContent());
        }
    }

}
