package com.example.jingjing.blogv6;

public class Movies {

    String movie;
    String movietime;
    float score;
    String picture;

    public Movies(String movie, String movietime, float score, String picture) {
        this.movie = movie;
        this.movietime = movietime;
        this.score = score;
        this.picture = picture;
    }

    public Movies() {
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getMovietime() {
        return movietime;
    }

    public void setMovietime(String movietime) {
        this.movietime = movietime;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
