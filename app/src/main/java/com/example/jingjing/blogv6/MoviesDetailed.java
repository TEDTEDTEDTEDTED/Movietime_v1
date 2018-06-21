package com.example.jingjing.blogv6;

public class MoviesDetailed {
    String name;
    String kind;
    String date;
    String picture;
    String plot;
    String trailer;
    int runtime;

    public MoviesDetailed(String name, String kind, String date, String picture, String plot, String trailer, int runtime) {
        this.name = name;
        this.kind = kind;
        this.date = date;
        this.picture = picture;
        this.plot = plot;
        this.trailer = trailer;
        this.runtime = runtime;
    }

    public MoviesDetailed() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }


}
