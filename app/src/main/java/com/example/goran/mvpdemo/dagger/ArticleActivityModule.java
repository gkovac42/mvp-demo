package com.example.goran.mvpdemo.dagger;


import com.example.goran.mvpdemo.articles.single.ArticleActivity;
import com.example.goran.mvpdemo.articles.single.ArticleContract;
import com.example.goran.mvpdemo.articles.single.ArticlePresenter;
import com.example.goran.mvpdemo.data.DataInteractor;
import com.example.goran.mvpdemo.data.Interactor;
import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.ApiHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Goran on 19.12.2017..
 */

@Module
public class ArticleActivityModule {

    private ArticleActivity articleActivity;

    public ArticleActivityModule(ArticleActivity activity) {
        this.articleActivity = activity;
    }

    @Provides
    @ActivityScope
    ArticleContract.View provideView() {
        return articleActivity;
    }

    @Provides
    @ActivityScope
    ArticleContract.Presenter providePresenter(ArticleContract.View view, Interactor interactor) {
        return new ArticlePresenter(view, interactor);
    }

    @Provides
    @ActivityScope
    Interactor provideInteractor(DatabaseHelper databaseHelper, SharedPrefsHelper sharedPrefsHelper, ApiHelper apiHelper) {
        return new DataInteractor(databaseHelper, sharedPrefsHelper, apiHelper);
    }
}
