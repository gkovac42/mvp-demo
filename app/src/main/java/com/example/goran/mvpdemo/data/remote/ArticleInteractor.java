package com.example.goran.mvpdemo.data.remote;

import com.example.goran.mvpdemo.articles.list.ListContract;
import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class ArticleInteractor implements RemoteInteractor {

    public ArrayList<Article> articles;
    private ListContract.Presenter presenter;
    private NetworkTask networkTask;

    public ArticleInteractor(ListContract.Presenter presenter) {
        this.presenter = presenter;
        networkTask = new NetworkTask(this);
    }

    @Override
    public void startDownload() {

        articles = new ArrayList<>();

        networkTask.execute(articles);
    }

    @Override
    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
        presenter.onDownloadComplete();
    }

    @Override
    public ArrayList<Article> getArticles() {
        return articles;
    }


}

