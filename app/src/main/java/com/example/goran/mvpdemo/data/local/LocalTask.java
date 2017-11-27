package com.example.goran.mvpdemo.data.local;

import android.os.AsyncTask;

import com.example.goran.mvpdemo.data.Article;
import com.example.goran.mvpdemo.data.Interactor;

import java.util.ArrayList;

/**
 * Created by Goran on 27.11.2017..
 */


public class LocalTask extends AsyncTask<ArrayList<Article>, Void, ArrayList<Article>> {

    private static final String START_DELIMITER = "introduction\">";
    private static final String END_DELIMITER = "Related Topics<";

    private DatabaseHelper dbHelper;
    private Interactor.DataListener listener;

    public LocalTask(DatabaseHelper dbHelper, Interactor.DataListener listener) {
        this.dbHelper = dbHelper;
        this.listener = listener;
    }

    public void removeListener() {
        if (this.listener != null) {
            this.listener = null;
        }
    }

    @SafeVarargs
    @Override
    protected final ArrayList<Article> doInBackground(ArrayList<Article>... articles) {

        articles[0] = dbHelper.getArticles();

        return articles[0];
    }

    @Override
    protected void onPostExecute(ArrayList<Article> result) {
        super.onPostExecute(result);

        if (listener != null) {
            listener.onDataReady(result);
        }
    }
}
