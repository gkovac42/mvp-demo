package com.example.goran.mvpdemo.data;

import com.example.goran.mvpdemo.data.remote.NetworkTask;

import java.util.ArrayList;

/**
 * Created by Goran on 20.11.2017..
 */

public interface Interactor {

    ArrayList<Article> getData(NetworkTask.Listener listener);

    ArrayList<Article> getLocalData();

    void getRemoteData();

    void saveData(ArrayList<Article> articles);

    void cancelRemoteDataTask();

    boolean timeToUpdate();


}
