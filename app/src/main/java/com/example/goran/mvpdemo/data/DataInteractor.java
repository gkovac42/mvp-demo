package com.example.goran.mvpdemo.data;

import android.content.Context;

import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.ArticleRequest;
import com.example.goran.mvpdemo.data.remote.ContentParser;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 17.11.2017..
 */

public class DataInteractor implements Interactor {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private DataListener listener;

    private Disposable dataDisposable;


    public DataInteractor(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.spHelper = SharedPrefsHelper.getInstance(context);
    }

    @Override
    public void getData(DataListener listener) {

        this.listener = listener;

        if (timeToUpdate()) {

            dataDisposable = getRemoteData();

        } else {

            dataDisposable = getLocalData();
        }
    }

    private Disposable getRemoteData() {

        return ArticleRequest.getObservable()
                .flatMap(articleResponse -> {

                    articleResponse.setArticles(ContentParser.parse(articleResponse.getArticles()));
                    return Observable.just(articleResponse.getArticles());
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        articles -> {
                            dbHelper.clearDatabase();
                            dbHelper.insertArticles(articles);
                            spHelper.setLastUpdateTime();
                            listener.onDataSuccess(articles);
                        },

                        throwable -> listener.onDataError());
    }

    private Disposable getLocalData() {

        return Observable.just(dbHelper.getArticles())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        articles -> listener.onDataSuccess(articles),

                        throwable -> listener.onDataError());
    }

    @Override
    public void dispose() {
        dataDisposable.dispose();
    }

    @Override
    public boolean timeToUpdate() {
        return spHelper.timeToUpdate();
    }
}

