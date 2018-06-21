package com.example.jingjing.blogv6;

public class Actor {
    String movie;
    String actor;
    String picture;

    public Actor(String movie, String actor, String picture) {
        this.movie = movie;
        this.actor = actor;
        this.picture = picture;
    }

    public Actor() {


    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }


}
