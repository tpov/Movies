package com.tpov.movies.api.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("id")
    private final int id;
    @SerializedName("original_language")
    private final String original_language;
    @SerializedName("original_title")
    private final String original_title;
    @SerializedName("overview")
    private final String overview;
    @SerializedName("popularity")
    private final double popularity;
    @SerializedName("poster_path")
    private final String poster_path;
    @SerializedName("release_date")
    private final String release_date;
    @SerializedName("title")
    private final String title;
    @SerializedName("video")
    private final Boolean video;
    @SerializedName("vote_average")
    private final double vote_average;
    @SerializedName("vote_count")
    private final int vote_count;

    public Movie(int id, String original_language, String original_title, String overview, int popularity, String poster_path, String release_date, String title, Boolean video, int vote_average, int vote_count) {
        this.id = id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }
}
