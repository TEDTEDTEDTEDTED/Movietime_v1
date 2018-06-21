package com.example.jingjing.blogv6;

public class Article {

    String name;
    String title;
    String article;
    String like;

    public Article(){

        }
    public Article(String name, String title, String article, String like){
        this.name=name;
        this.title=title;
        this.article=article;
        this.like=like;
    }


    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getArticle() {
        return article;
    }

    public String getLike() {
        return like;
    }
}
