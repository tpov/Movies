package com.tpov.movies.api;

import com.tpov.movies.api.pojo.Response;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

//        URL: popular
//        URL: top_rated
//        URL: now_playing

public interface ApiService {

    @GET("now_playing?api_key=97caa9884c8bd0dbad7c6aeb2e63c259&language=en-US")
    Single<Response> loadMoviesNowPlaying(@Query("page") int page);

    @GET("top_rated?api_key=97caa9884c8bd0dbad7c6aeb2e63c259&&language=en-US&page=1")
    Single<Response> loadMoviesTopRated(@Query("page") int page);

    @GET("popular?api_key=97caa9884c8bd0dbad7c6aeb2e63c259&&language=en-US&page=1")
    Single<Response> loadMoviesPopular(@Query("page") int page);
}
