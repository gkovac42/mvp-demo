package com.example.goran.mvpdemo.articles.list;

import com.example.goran.mvpdemo.data.Article;
import com.example.goran.mvpdemo.data.DataInteractor;
import com.example.goran.mvpdemo.data.Interactor;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class ListPresenter implements ListContract.Presenter, DataInteractor.Listener {

    private ListContract.View view;
    private Interactor dataInteractor;
    private ArrayList<Article> articles;

    public ListPresenter(ListContract.View view, Interactor interactor) {
        this.view = view;
        this.dataInteractor = interactor;
        this.dataInteractor.setListener(ListPresenter.this);
    }


    @Override
    public void getArticleData() {

        if (dataInteractor.timeToUpdate()) {

            dataInteractor.getRemoteData();

        } else {

            articles = dataInteractor.getLocalData();

            view.updateList(articles);
        }
    }

    @Override
    public void onRemoteDataReady(ArrayList<Article> articles) {

        this.articles = articles;

        if (articles != null && articles.size() != 0) {

            dataInteractor.saveData(articles);

            view.updateList(articles);

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
        dataInteractor.setListener(null);
    }
}
