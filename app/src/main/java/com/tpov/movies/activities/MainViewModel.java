package com.tpov.movies.activities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tpov.movies.api.pojo.Movie;
import com.tpov.movies.api.pojo.Response;
import com.tpov.movies.api.ApiFactory;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";
    private final MutableLiveData<List<Movie>> moviesNowPlaying = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposableNowPlaying = new CompositeDisposable();

    private final MutableLiveData<List<Movie>> moviesTopRated = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposableTopRated = new CompositeDisposable();

    private final MutableLiveData<List<Movie>> moviesPopular = new MutableLiveData<>();
    private final CompositeDisposable compositeDisposablePopular = new CompositeDisposable();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Movie>> getMoviesNowPlaying() {
        return moviesNowPlaying;
    }

    public LiveData<List<Movie>> getMoviesTopRated() {
        return moviesTopRated;
    }

    public LiveData<List<Movie>> getMoviePopular() {
        return moviesPopular;
    }

    //RxJava
    public void loadMoviesNowPlaying(int pageNowPlaying) {
        Disposable disposable = ApiFactory.apiService.loadMoviesNowPlaying(pageNowPlaying)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Throwable {
                        moviesNowPlaying.postValue(response.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposableNowPlaying.add(disposable);
    }

    public void loadMoviesTopRated(int pageTopRated) {
        Disposable disposable = ApiFactory.apiService.loadMoviesTopRated(pageTopRated)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Throwable {
                        moviesTopRated.postValue(response.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposableTopRated.add(disposable);
    }

    public void loadMoviesPopular(int pagePopular) {
        Disposable disposable = ApiFactory.apiService.loadMoviesPopular(pagePopular)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response>() {
                    @Override
                    public void accept(Response response) throws Throwable {
                        moviesPopular.postValue(response.getResults());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        Log.d(TAG, throwable.toString());
                    }
                });
        compositeDisposablePopular.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposableNowPlaying.dispose();
        compositeDisposableTopRated.dispose();
        compositeDisposablePopular.dispose();
    }
}
