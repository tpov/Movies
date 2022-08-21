package com.tpov.movies.activities;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.tpov.movies.R;
import com.tpov.movies.api.pojo.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private final String urlPage = "https://image.tmdb.org/t/p/w500";

    private List<Movie> movies = new ArrayList<>();
    private int positionTab;

    private OnMovieClickListener onMovieClickListener;

    public void setMovies(List<Movie> movies, int position) {
        this.positionTab = position;
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
            Movie movie = movies.get(position);
            Glide.with(holder.itemView)
                    .load(urlPage + movie.getPoster_path())
                    .into(holder.imageViewPoster);
            holder.textViewRating.setText(getTextByPosition(positionTab, movie));

            holder.imageViewPoster.setOnClickListener(v -> {
                if (onMovieClickListener != null) {
                    onMovieClickListener.onMovieClick(movie);
                }
            });
    }

    private String getTextByPosition(int positionTab, Movie movie) {
        if (positionTab == 0) return String.valueOf(movie.getVote_average());
           else if (positionTab == 1) return String.valueOf(movie.getPopularity());
           else return String.valueOf(movie.getRelease_date());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends ViewHolder {

        private final ImageView imageViewPoster;
        private final TextView textViewRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPoster = itemView.findViewById(R.id.im_view_poster);
            textViewRating = itemView.findViewById(R.id.tv_rating);

        }
    }
}
