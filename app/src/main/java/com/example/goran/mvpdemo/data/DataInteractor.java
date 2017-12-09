package com.example.goran.mvpdemo.data;

import android.util.Log;

import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.ArticleRequest;
import com.example.goran.mvpdemo.data.remote.ContentParser;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 17.11.2017..
 */

public class DataInteractor implements Interactor {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private CompositeDisposable compositeDisposable;


    public DataInteractor(DatabaseHelper dbHelper, SharedPrefsHelper spHelper) {
        this.compositeDisposable = new CompositeDisposable();
        this.dbHelper = dbHelper;
        this.spHelper = spHelper;
    }

    @Override
    public void getData(DataListener listener) {

        Observable<ArrayList<Article>> dataObservable;

        if (timeToUpdate()) {

            dataObservable = getRemoteData();

        } else {

            dataObservable = getLocalData();
        }


        dataObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(

                        articles -> listener.onDataSuccess(articles),

                        throwable -> listener.onDataError(),

                        () -> Log.i("M", "Complete"),

                        disposable -> compositeDisposable.add(disposable));
    }

    private Observable<ArrayList<Article>> getRemoteData() {

        return ArticleRequest.getObservable()
                .flatMap(articleResponse -> {

                    articleResponse.setArticles(ContentParser.parse(articleResponse.getArticles()));

                    dbHelper.clearDatabase();
                    dbHelper.insertArticles(articleResponse.getArticles());
                    spHelper.setLastUpdateTime();

                    return Observable.just(articleResponse.getArticles());
                });
    }

    private Observable<ArrayList<Article>> getLocalData() {

        return Observable.just(dbHelper.getArticles());
    }

    @Override
    public void dispose() {
        compositeDisposable.dispose();
    }

    @Override
    public boolean timeToUpdate() {
        return spHelper.timeToUpdate();
    }
}

