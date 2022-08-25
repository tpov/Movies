package com.tpov.movies.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;
import com.tpov.movies.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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


        moviesAdapter.setOnMovieClickListener(movie -> {
            Intent intent = DetailMovieActivity.newIntent(MainActivity.this, movie);
            startActivity(intent);
        });
    }

    private void observe() {
        viewModel.getProgressBarVisible().observe(this, visible -> progressBar.setVisibility(visible));
        viewModel.getStartAdapter().observe(this, new Observer<List>() {
            @Override
            public void onChanged(List list) {
                startAdapter(list);
            }
        });
    }

    private void startAdapter(List list) {

        viewModel.progressBarVisibleLiveData(MainViewModel.VISIBLE_PB);
        moviesAdapter.setMovies(list, viewModel.position);
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        viewModel.progressBarVisibleLiveData(MainViewModel.GONE_PB);
    }

    private void initView() {
        recyclerView = findViewById(R.id.rv_movies);
        progressBar = findViewById(R.id.pb_loading);
        tabLayout = findViewById(R.id.tl_category);
    }

    private void initObserve() {
        viewModel.loadMoviesNowPlaying(MainViewModel.PAGE_NOW_PLAYING);
        viewModel.loadMoviesTopRated(MainViewModel.PAGE_TOP_RATED);
        viewModel.loadMoviesPopular(MainViewModel.PAGE_POPULAR);
    }
}