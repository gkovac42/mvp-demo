package com.example.goran.mvpdemo.articles.list;

import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public interface ListContract {

    interface View {

        void updateArticles(ArrayList<Article> articles);

        void navigateToArticle(int position);

        void showErrorDialog();

    }

    interface Presenter {

        void getArticleData();

        void onArticleClick(int position);

        void onDestroyPresenter();

    }
}
