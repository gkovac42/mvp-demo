package com.example.goran.mvpdemo.data;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.ApiHelper;
import com.example.goran.mvpdemo.data.remote.ContentParser;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Goran on 17.11.2017..
 */

@Singleton
public class DataInteractor implements Interactor, LifecycleObserver {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private ApiHelper apiHelper;
    private CompositeDisposable compositeDisposable;

    @Inject
    public DataInteractor(DatabaseHelper dbHelper, SharedPrefsHelper spHelper, ApiHelper apiHelper) {
        this.dbHelper = dbHelper;
        this.spHelper = spHelper;
        this.apiHelper = apiHelper;
        this.compositeDisposable = new CompositeDisposable();
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

                        () -> Log.i("MSG", "Complete"),

                        disposable -> compositeDisposable.add(disposable));
    }

    private Observable<ArrayList<Article>> getRemoteData() {

        return apiHelper.getDataObservable()
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
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void dispose() {
        compositeDisposable.dispose();
        Log.i("MSG", "Destroyed");
    }

    @Override
    public boolean timeToUpdate() {
        return spHelper.timeToUpdate();
    }
}

