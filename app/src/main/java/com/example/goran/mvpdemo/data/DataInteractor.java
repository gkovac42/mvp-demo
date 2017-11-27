package com.example.goran.mvpdemo.data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.goran.mvpdemo.data.local.DatabaseHelper;
import com.example.goran.mvpdemo.data.local.LocalTask;
import com.example.goran.mvpdemo.data.local.SharedPrefsHelper;
import com.example.goran.mvpdemo.data.remote.RemoteTask;

import java.util.ArrayList;

/**
 * Created by Goran on 17.11.2017..
 */

public class DataInteractor implements Interactor {

    private DatabaseHelper dbHelper;
    private SharedPrefsHelper spHelper;
    private LocalTask localTask;
    private RemoteTask remoteTask;

    public DataInteractor(Context context) {
        this.dbHelper = DatabaseHelper.getInstance(context);
        this.spHelper = SharedPrefsHelper.getInstance(context);

    }

    @Override
    public void getData(DataListener listener) {

        if (timeToUpdate()) {

            remoteTask = new RemoteTask(dbHelper, listener);
            spHelper.setLastUpdateTime();
            getRemoteData();

        } else {
            localTask = new LocalTask(dbHelper, listener);
            getLocalData();
        }
    }

    private void getRemoteData() {

        ArrayList<Article> articles = new ArrayList<>();

        remoteTask.execute(articles);
    }

    private void getLocalData() {

        ArrayList<Article> articles = new ArrayList<>();

        localTask.execute(articles);

    }

    @Override
    public void cancelDataTask() {

        if (remoteTask != null) {
            remoteTask.removeListener();

            if (remoteTask.getStatus() != AsyncTask.Status.FINISHED) {
                remoteTask.cancel(true);
            }

        } else if (localTask != null) {
            localTask.removeListener();

            if (localTask.getStatus() != AsyncTask.Status.FINISHED) {
                localTask.cancel(true);
            }
        }
    }

    @Override
    public boolean timeToUpdate() {
        return spHelper.timeToUpdate();
    }
}

