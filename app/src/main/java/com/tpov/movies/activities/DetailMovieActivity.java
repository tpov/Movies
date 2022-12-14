package com.tpov.movies.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tpov.movies.R;
import com.tpov.movies.api.pojo.Movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class DetailMovieActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE = "movie";
    private static final String urlImage = "https://image.tmdb.org/t/p/w500";

    private ImageView imageMovie;
    private TextView tvTitle;
    private TextView tvVoteAverage;
    private TextView tvReleaseDate;
    private TextView tvPopularity;
    private TextView tvOverview;


    @SuppressLint("SetTextI18n")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        initView();

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);


        Glide.with(this)
                .load(urlImage + movie.getPoster_path())
                .into(imageMovie);
        tvTitle.setText(movie.getTitle());
        tvVoteAverage.setText(String.valueOf(R.string.vote_average) + movie.getVote_average());
        tvReleaseDate.setText(String.valueOf(R.string.release_date) + movie.getRelease_date());
        tvPopularity.setText(String.valueOf(R.string.popularity) + movie.getVote_count());
        tvOverview.setText(movie.getOverview());
    }

    private void initView() {
        imageMovie = findViewById(R.id.imv_movie);
        tvTitle = findViewById(R.id.tv_title);
        tvVoteAverage = findViewById(R.id.tv_vote_average);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvPopularity = findViewById(R.id.tv_popularity);
        tvOverview = findViewById(R.id.tv_overview);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, DetailMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}