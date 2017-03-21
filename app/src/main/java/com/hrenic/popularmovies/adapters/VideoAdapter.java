package com.hrenic.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrenic.popularmovies.R;
import com.hrenic.popularmovies.data.Video;

import java.util.List;

/**
 * Adapter for showing videos on detail page
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public interface VideOnClickHandler {
        void onClick(Video video);
    }

    private Context context;
    private VideOnClickHandler mClickHandler;
    private List<Video> videos;

    public VideoAdapter(@NonNull Context context, VideOnClickHandler clickHandler) {
        this.context = context;
        mClickHandler = clickHandler;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.setVideo(videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mVideoNameTextView;

        VideoViewHolder(View itemView) {
            super(itemView);
            mVideoNameTextView = (TextView) itemView.findViewById(R.id.video_text);
            itemView.setOnClickListener(this);
        }

        void setVideo(Video video) {
            mVideoNameTextView.setText(video.getName());
        }

        @Override
        public void onClick(View v) {
            if (mClickHandler != null) {
                mClickHandler.onClick(videos.get(getAdapterPosition()));
            }
        }
    }
}
