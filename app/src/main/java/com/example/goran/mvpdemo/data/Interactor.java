package com.example.goran.mvpdemo.data;

import android.arch.lifecycle.LifecycleObserver;

import java.util.ArrayList;

/**
 * Created by Goran on 20.11.2017..
 */

public interface Interactor extends LifecycleObserver {

    void getData(DataListener listener);

    void dispose();

    boolean timeToUpdate();

    interface DataListener {

        void onDataSuccess(ArrayList<Article> articles);

        void onDataError();
    }
}
