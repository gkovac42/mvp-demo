package com.example.goran.mvpdemo.articles.single;

import com.example.goran.mvpdemo.data.Article;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public interface ArticleContract {

    interface View {

        void updateArticles(ArrayList<Article> articles);
    }

    interface Presenter {

        ArrayList<Article> getArticleData();
    }
}
