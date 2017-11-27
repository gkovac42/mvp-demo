package com.example.goran.mvpdemo.articles.list;

import com.example.goran.mvpdemo.data.Article;
import com.example.goran.mvpdemo.data.Interactor;
import com.example.goran.mvpdemo.data.remote.NetworkTask;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class ListPresenter implements ListContract.Presenter, NetworkTask.Listener {

    private ListContract.View view;
    private Interactor dataInteractor;
    private ArrayList<Article> articles;

    public ListPresenter(ListContract.View view, Interactor interactor) {
        this.view = view;
        this.dataInteractor = interactor;
    }


    @Override
    public void getArticleData() {

        articles = dataInteractor.getData(this);

        if (articles != null) {

            view.updateArticles(articles);
        }
    }

    @Override
    public void onDataReady(ArrayList<Article> articles) {

        if (articles != null) {

            view.updateArticles(articles);

            dataInteractor.saveData(articles);

        } else {

            view.showErrorDialog();
        }
    }

    @Override
    public void onArticleClick(int position) {

        view.navigateToArticle(position, articles);
    }

    @Override
    public void onDestroyPresenter() {
        dataInteractor.cancelRemoteDataTask();
    }
}
