package com.example.goran.mvpdemo.data;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
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

public class DataInteractor implements Interactor, LifecycleObserver {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private CompositeDisposable compositeDisposable;
    private Lifecycle lifecycle;


    public DataInteractor(DatabaseHelper dbHelper, SharedPrefsHelper spHelper, LifecycleOwner lifecycleOwner) {
        this.compositeDisposable = new CompositeDisposable();
        this.dbHelper = dbHelper;
        this.spHelper = spHelper;
        this.lifecycle = lifecycleOwner.getLifecycle();
        this.lifecycle.addObserver(this);
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

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        Log.i("ON DESTROY", "ON DESTROY!!!!!");
        compositeDisposable.dispose();

    }
}

