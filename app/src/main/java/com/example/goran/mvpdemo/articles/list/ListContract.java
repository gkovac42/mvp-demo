package com.example.goran.mvpdemo.articles.list;

import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public interface ListContract {

    interface View {

        void showArticleList(ArrayList<Article> articles);

        void navigateToArticle(int position, ArrayList<Article> articleData);

        void showErrorDialog();

    }

    interface Presenter {

        void getArticleData();

        void onDownloadComplete();

        void onArticleClick(int position);


    }

}
