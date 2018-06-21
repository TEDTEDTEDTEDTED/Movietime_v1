package com.example.jingjing.blogv6;

public class News {

    String date;
    String genre;
    String text;
    String title;
    String writer;

    public News(String date, String genre, String text, String title, String writer) {
        this.date = date;
        this.genre = genre;
        this.text = text;
        this.title = title;
        this.writer = writer;
    }

    public News() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
