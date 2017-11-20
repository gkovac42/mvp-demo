package com.example.goran.mvpdemo.articles.list;

import com.example.goran.mvpdemo.data.Article;
import com.example.goran.mvpdemo.data.remote.ArticleInteractor;
import com.example.goran.mvpdemo.data.remote.RemoteInteractor;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class ListPresenter implements ListContract.Presenter {


    private ListContract.View view;
    private RemoteInteractor remoteInteractor;
    private ArrayList<Article> articles;

    public ListPresenter(ListContract.View view) {
        this.view = view;
        remoteInteractor = new ArticleInteractor(this);
    }


    @Override
    public void getArticleData() {

        remoteInteractor.startDownload();

        //TODO get local data
    }

    @Override
    public void onDownloadComplete() {

        articles = remoteInteractor.getArticles();

        // TODO save article data

        view.showArticleList(articles);
    }

    @Override
    public void onArticleClick(int position) {

        view.navigateToArticle(position, articles);
    }
}
