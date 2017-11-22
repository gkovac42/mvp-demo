package com.example.goran.mvpdemo.data;

import java.util.ArrayList;

/**
 * Created by Goran on 20.11.2017..
 */

public interface Interactor {

    void getRemoteData(DataInteractor.Listener listener);

    ArrayList<Article> getLocalData();

    void saveData(ArrayList<Article> articles);

    void cancelRemoteDataTask();

    boolean timeToUpdate();




}
