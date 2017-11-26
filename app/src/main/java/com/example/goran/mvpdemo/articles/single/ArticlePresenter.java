package com.example.goran.mvpdemo.articles.single;

import com.example.goran.mvpdemo.data.Article;
import com.example.goran.mvpdemo.data.Interactor;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class ArticlePresenter implements ArticleContract.Presenter {

    private ArticleContract.View view;
    private Interactor dataInteractor;
    private ArrayList<Article> articles;

    public ArticlePresenter(ArticleContract.View view, Interactor interactor) {
        this.view = view;
        this.dataInteractor = interactor;
    }

    @Override
    public void getArticleData() {

        articles = dataInteractor.getLocalData();

        view.updateArticles(articles);
    }

    @Override
    public void onArticleSelected(int position) {
        view.updateTitle(articles.get(position).getTitle());
    }
}
