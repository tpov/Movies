package com.tpov.movies.api.pojo;

import com.google.gson.annotations.SerializedName;
import com.tpov.movies.api.pojo.Movie;

import java.util.List;

public class Response {
    @SerializedName("results")
    private List<Movie> results;

    public Response(List<Movie> results) {
        this.results = results;
    }

    public List<Movie> getResults() {
        return results;
    }

}
