package com.example.goran.mvpdemo.data;

import java.util.ArrayList;

/**
 * Created by Goran on 20.11.2017..
 */

public interface Interactor {

    void getData(DataListener listener);

    void dispose();

    boolean timeToUpdate();

    interface DataListener {

        void onDataReady(ArrayList<Article> articles);
    }
}
