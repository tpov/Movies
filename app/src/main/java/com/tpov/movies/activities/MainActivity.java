package com.tpov.movies.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.tpov.movies.R;

/**
 * Link to the page
 * https://api.themoviedb.org/3/movie/now_playing?api_key=97caa9884c8bd0dbad7c6aeb2e63c259&&language=en-US&page=1
 * URL: popular
 * URL: top_rated
 * URL: now_playing
 * <p>
 * Link to the image
 * "https://image.tmdb.org/t/p/w500" + movie.getPoster_path()
 */


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private TabLayout tabLayout;
    private ProgressBar progressBar;

    private MainViewModel viewModel;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        moviesAdapter = new MoviesAdapter();

        initView();
        observe();
        initObserve();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewModel.position = tab.getPosition();
                viewModel.loadPage(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        moviesAdapter.setOnMovieClickListener(movie -> {
            Intent intent = DetailMovieActivity.newIntent(MainActivity.this, movie);
            startActivity(intent);
        });
    }

    private void observe() {
        viewModel.getProgressBarVisible().observe(this, visible -> progressBar.setVisibility(visible));

        viewModel.getMoviesNowPlaying().observe(this, movies -> viewModel.moviesNowPlayingList = movies);

        viewModel.getMoviesTopRated().observe(this, movies -> {
            viewModel.moviesTopRatedList = movies;
            viewModel.startAdapter(movies);   //Отображаем список который отображается при запуске приложения
        });

        viewModel.getMoviePopular().observe(this, movies -> viewModel.moviesPopularList = movies);

        viewModel.progressBarVisibleLiveData(viewModel.VISIBLE_PB);
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_movies);
        progressBar = findViewById(R.id.pb_loading);
        tabLayout = findViewById(R.id.tl_category);
    }

    private void initObserve() {
        viewModel.loadMoviesNowPlaying(viewModel.PAGE_NOW_PLAYING);
        viewModel.loadMoviesTopRated(viewModel.PAGE_TOP_RATED);
        viewModel.loadMoviesPopular(viewModel.PAGE_POPULAR);

    }
}