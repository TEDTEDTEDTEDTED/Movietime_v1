package com.example.jingjing.blogv6;

public class Trailer {
    String movie;
    String trailer;

    public Trailer() {
    }

    public Trailer(String movie, String trailer) {
        this.movie = movie;
        this.trailer = trailer;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
