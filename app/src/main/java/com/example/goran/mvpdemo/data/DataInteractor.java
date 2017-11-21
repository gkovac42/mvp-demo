package com.example.goran.mvpdemo.data;

import android.content.Context;

import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.NetworkTask;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class DataInteractor implements Interactor, NetworkTask.Listener {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private ArrayList<Article> articles;
    private NetworkTask networkTask;

    private static DataInteractor instance = null;

    public static DataInteractor getInstance(Context context) {
        if (instance == null) {
            instance = new DataInteractor(
                    DatabaseHelper.getInstance(context),
                    SharedPrefsHelper.getInstance(context));
        }
        return instance;
    }

    private DataInteractor(DatabaseHelper dbHelper, SharedPrefsHelper spHelper) {
        this.dbHelper = dbHelper;
        this.spHelper = spHelper;
        networkTask = new NetworkTask(DataInteractor.this);

    }

    private Listener listener;

    @Override
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onRemoteDataReady(ArrayList<Article> articles);
    }


    @Override
    public void getRemoteData() {

        articles = new ArrayList<>();

        networkTask.execute(articles);
    }

    @Override
    public ArrayList<Article> getLocalData() {

        return dbHelper.getArticles();
    }

    @Override
    public void saveData(ArrayList<Article> articles) {

        dbHelper.clearDatabase();
        dbHelper.insertArticles(articles);
        spHelper.setLastUpdateTime();
    }

    @Override
    public void onTaskComplete(ArrayList<Article> articles) {

        listener.onRemoteDataReady(articles);
    }

    @Override
    public boolean timeToUpdate() {
        return spHelper.timeToUpdate();
    }
}

