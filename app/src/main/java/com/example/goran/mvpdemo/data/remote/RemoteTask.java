package com.example.goran.mvpdemo.data.remote;

import android.os.AsyncTask;
import android.text.Html;

import com.example.goran.mvpdemo.data.Article;
import com.example.goran.mvpdemo.data.Interactor;
import com.example.goran.mvpdemo.data.local.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Goran on 20.11.2017..
 */

public class RemoteTask extends AsyncTask<ArrayList<Article>, Void, ArrayList<Article>> {

    private static final String START_DELIMITER = "introduction\">";
    private static final String END_DELIMITER = "Related Topics<";

    private DatabaseHelper dbHelper;
    private Interactor.DataListener listener;

    public RemoteTask(DatabaseHelper dbHelper, Interactor.DataListener listener) {
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

        // retrofit related stuff
        Call<ArticleResponse> apiCall = ArticleRequest.initApiCall();

        try {

            Response<ArticleResponse> apiResponse = apiCall.execute();

            articles[0] = apiResponse.body().getArticles();

            // parse article content from url
            for (Article article : articles[0]) {
                try {
                    // get HTML from URL
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(article.getUrl())
                            .build();

                    okhttp3.Response urlResponse = client.newCall(request).execute();

                    // split HTML to get article content
                    String splitHeader = urlResponse.body().string().split(START_DELIMITER)[1];
                    String splitFooter = splitHeader.split(END_DELIMITER)[0];

                    // remove HTML tags
                    article.setContent(Html.fromHtml(splitFooter).toString());

                } catch (Exception e) {
                    e.printStackTrace();
                    article.setContent("Parsiranje sadržaja nije uspjelo.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return articles[0];
    }

    @Override
    protected void onPostExecute(ArrayList<Article> result) {
        super.onPostExecute(result);


        if (listener != null) {
            listener.onDataReady(result);
        }

        dbHelper.insertArticles(result);
    }


}