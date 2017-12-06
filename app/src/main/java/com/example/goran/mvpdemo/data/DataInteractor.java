package com.example.goran.mvpdemo.data;

import android.content.Context;

import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.ArticleRequest;
import com.example.goran.mvpdemo.data.remote.ArticleResponse;
import com.example.goran.mvpdemo.data.remote.ContentParser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 17.11.2017..
 */

public class DataInteractor implements Interactor {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private DataListener listener;

    private Observable<ArticleResponse> dataObservable;


    public DataInteractor(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.spHelper = SharedPrefsHelper.getInstance(context);
    }

    @Override
    public void getData(DataListener listener) {

        this.listener = listener;

        if (timeToUpdate()) {

            dataObservable = getRemoteObservable();

        } else {

            dataObservable = getLocalObservable();

        }
    }

    private Observable<ArticleResponse> getRemoteObservable() {

        Observable<ArticleResponse> dataObservable = ArticleRequest.getObservable();

        dataObservable.flatMap(articleResponse -> {

            articleResponse.setArticles(ContentParser.parse(articleResponse.getArticles()));

            return Observable.just(articleResponse);
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articleResponse -> {

                    dbHelper.clearDatabase();
                    dbHelper.insertArticles(articleResponse.getArticles());
                    spHelper.setLastUpdateTime();

                    listener.onDataReady(articleResponse.getArticles());
                });

        return dataObservable;
    }

    private Observable<ArticleResponse> getLocalObservable() {

        Observable<ArticleResponse> dataObservable = Observable.just(dbHelper.getArticles());

        dataObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articleResponse -> listener.onDataReady(articleResponse.getArticles()));

        return dataObservable;
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean timeToUpdate() {
        return spHelper.timeToUpdate();
    }
}

