package com.example.goran.mvpdemo.data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.NetworkTask;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class DataInteractor implements Interactor {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private NetworkTask networkTask;

    public DataInteractor(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.spHelper = SharedPrefsHelper.getInstance(context);

    }

    @Override
    public ArrayList<Article> getData(NetworkTask.Listener listener) {

        if (timeToUpdate()) {

            networkTask = new NetworkTask(listener);
            getRemoteData();
            return null;

        } else {

            return getLocalData();
        }
    }

    @Override
    public void getRemoteData() {

        ArrayList<Article> articles = new ArrayList<>();

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
    public void cancelRemoteDataTask() {

        if (networkTask.getStatus() != AsyncTask.Status.FINISHED) {
            networkTask.cancel(true);
        }

        networkTask.removeListener();
    }

    @Override
    public boolean timeToUpdate() {
        return spHelper.timeToUpdate();
    }
}

