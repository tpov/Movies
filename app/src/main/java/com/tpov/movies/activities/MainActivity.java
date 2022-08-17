package com.tpov.movies.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.tpov.movies.api.pojo.Movie;
import com.tpov.movies.R;

import java.util.List;

//https://api.themoviedb.org/3/movie/now_playing?api_key=97caa9884c8bd0dbad7c6aeb2e63c259&&language=en-US&page=1
//"https://image.tmdb.org/t/p/w500" + movie.getPoster_path()

//        URL: popular
//        URL: top_rated
//        URL: now_playing

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final int pageNowPlaying = 1;
    private final int pageTopRated = 1;
    private final int pagePopular = 1;
    private int position = 0;

    private List<Movie> moviesNowPlaying;
    private List<Movie> moviesTopRated;
    private List<Movie> moviesPopular;
    private List<Movie> movies10;

    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private TabLayout tabLayout;
    private Button bNext;
    private ProgressBar progressBar;
    private CardView cardView;

    private String[] arrayNames = {
            "popular",
            "top_rated",
            "now_playing"
    };

    private MainViewModel viewModel;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        recyclerView = findViewById(R.id.rv_movies);
        bNext = findViewById(R.id.b_next);
        progressBar = findViewById(R.id.pb_loading);

        moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        moviesAdapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = DetailMovieActivity.newIntent(MainActivity.this, movie);
                startActivity(intent);
            }
        });
/*
        TabLayoutMediator(tabLayout, recyclerView) {
            position
        }*/

        viewModel.getMoviesNowPlaying().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesNowPlaying = movies;
//                for (int i = 0; i < movies.size(); i++) {
//                    if (i <= 10) {
//                        moviesNowPlaying.set(i, movies.get(i));
//                    }
//                }

            }
        });

        viewModel.getMoviesTopRated().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesTopRated = movies;
//                for (int i = 0; i < movies.size(); i++) {
//                    if (i <= 10) {
//                        moviesTopRated.set(i, movies.get(i));
//                    }
//                }
                startAdapter(moviesTopRated);
            }
        });

        viewModel.getMoviePopular().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesPopular = movies;
//                for (int i = 0; i < movies.size(); i++) {
//                    if (i <= 10) {
//                        moviesPopular.set(i, movies.get(i));
//                    }
//                }
            }
        });

        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 2) {
                    position = 0;
                } else {
                    position++;
                }
                loadPage(position);
            }
        });

        viewModel.loadMoviesNowPlaying(pageNowPlaying);
        viewModel.loadMoviesTopRated(pageTopRated);
        viewModel.loadMoviesPopular(pagePopular);
    }

    private void loadPage(int p) {
        if (p == 0) {
            startAdapter(moviesTopRated);
        } else if (p == 1) {
            startAdapter(moviesPopular);
        } else if (p == 2) {
            startAdapter(moviesNowPlaying);
        }

    }

    private void startAdapter(List<Movie> list) {
        for (int i = 0; i < list.size(); i++) {
            if (i > 3) {
                list.remove(i);
            }
        }
        moviesAdapter.setMovies(list);
        progressBar.setVisibility(View.GONE);
    }
}