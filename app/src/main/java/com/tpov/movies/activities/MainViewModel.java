package com.tpov.movies.activities;

import android.app.Application;
import android.util.Log;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tpov.movies.activities.MoviesAdapter;
import com.tpov.movies.api.ApiFactory;
import com.tpov.movies.api.pojo.Movie;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    public static final int TOP_RADE_LIST = 0;
    public static final int POPULAR_LIST = 1;
    public static final int NOW_PLAYING_LIST = 2;
    public static final int COUNT_FILMS_IN_PAGE = 10;

    public static final int PAGE_NOW_PLAYING = 1;
    public static final int PAGE_TOP_RATED = 1;
    public static final int PAGE_POPULAR = 1;

    public static final int VISIBLE_PB = View.VISIBLE;
    public static final int GONE_PB = View.GONE;

    public int position = 0;
    public List<Movie> moviesNowPlayingList;
    public List<Movie> moviesTopRatedList;
    public List<Movie> moviesPopularList;

    private final CompositeDisposable compositeDisposableNowPlaying = new CompositeDisposable();

    private final CompositeDisposable compositeDisposableTopRated = new CompositeDisposable();

    private final CompositeDisposable compositeDisposablePopular = new CompositeDisposable();

    private final MutableLiveData<Integer> progressBarVisible = new MutableLiveData<>();
    
    private final MutableLiveData<List> startAdapter = new MutableLiveData<>();

    public LiveData<Integer> getProgressBarVisible() {
        return progressBarVisible;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
    
    //RxJava
    public void loadMoviesNowPlaying(int pageNowPlaying) {
        Disposable disposable = ApiFactory.apiService.loadMoviesNowPlaying(pageNowPlaying)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> moviesNowPlayingList = response.getResults(),
                        throwable -> {

                        });
        compositeDisposableNowPlaying.add(disposable);
    }

    public void loadMoviesTopRated(int pageTopRated) {
        Disposable disposable = ApiFactory.apiService.loadMoviesTopRated(pageTopRated)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            moviesTopRatedList = response.getResults();
                            startAdapter(response.getResults());   //???????????????????? ???????????? ?????????????? ???????????????????????? ?????? ?????????????? ????????????????????
                        },
                        throwable -> {

                        });
        compositeDisposableTopRated.add(disposable);

    }

    public void loadMoviesPopular(int pagePopular) {
        Disposable disposable = ApiFactory.apiService.loadMoviesPopular(pagePopular)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> moviesPopularList = response.getResults());
        compositeDisposablePopular.add(disposable);
    }

    public void loadPage(int position) {
        if (position == TOP_RADE_LIST) {
            startAdapter(moviesTopRatedList);
        } else if (position == POPULAR_LIST) {
            startAdapter(moviesPopularList);
        } else if (position == NOW_PLAYING_LIST) {
            startAdapter(moviesNowPlayingList);
        }
    }

    public void startAdapter(List<Movie> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.size() > COUNT_FILMS_IN_PAGE) {
                list.remove(i);
            }
        }
        startAdapterLivedata(list);

    }

    public void progressBarVisibleLiveData(Integer visible) {
        progressBarVisible.postValue(visible);
    }

    public void startAdapterLivedata(List<Movie> list) {
        startAdapter.postValue(list);
    }

    public LiveData<List> getStartAdapter() {
        return startAdapter;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposableNowPlaying.dispose();
        compositeDisposableTopRated.dispose();
        compositeDisposablePopular.dispose();
    }
}