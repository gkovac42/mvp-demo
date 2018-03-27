package com.example.goran.mvpdemo.articles.list;

import com.example.goran.mvpdemo.data.model.Article;
import com.example.goran.mvpdemo.data.Interactor;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by Goran on 17.11.2017..
 */

public class ListPresenter implements ListContract.Presenter, Interactor.DataListener {

    private ListContract.View view;
    private Interactor dataInteractor;

    @Inject
    public ListPresenter(ListContract.View view, Interactor interactor) {
        this.view = view;
        this.dataInteractor = interactor;
    }


    @Override
    public void getArticleData() {
        dataInteractor.getData(this);
    }

    @Override
    public void onDataSuccess(ArrayList<Article> articles) {
        view.updateArticles(articles);
    }

    @Override
    public void onDataError() {
        view.showErrorDialog();
    }

    @Override
    public void onArticleClick(int position) {
        view.navigateToArticle(position);
    }

    @Override
    public void onDestroyPresenter() {
        dataInteractor.dispose();
    }
}
