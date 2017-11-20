package com.example.goran.mvpdemo.data.remote;

import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 20.11.2017..
 */

public interface RemoteInteractor {

    void startDownload();

    void setArticles(ArrayList<Article> articles);

    ArrayList<Article> getArticles();
}
