package com.example.goran.mvpdemo.data.remote;

import com.example.goran.mvpdemo.data.model.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 13.11.2017..
 */

public class ArticleResponse {

    private ArrayList<Article> articles;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }
}
