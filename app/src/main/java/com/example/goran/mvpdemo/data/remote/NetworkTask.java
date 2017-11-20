package com.example.goran.mvpdemo.data.remote;

import android.os.AsyncTask;
import android.text.Html;

import com.example.goran.mvpdemo.data.Article;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Goran on 20.11.2017..
 */

public class NetworkTask extends AsyncTask<ArrayList<Article>, Void, ArrayList<Article>> {

    private static final String START_DELIMITER = "introduction\">";
    private static final String END_DELIMITER = "Related Topics<";

    private RemoteInteractor remoteInteractor;

    public NetworkTask(RemoteInteractor remoteInteractor) {
        this.remoteInteractor = remoteInteractor;
    }

    @SafeVarargs
    @Override
    protected final ArrayList<Article> doInBackground(ArrayList<Article>... articles) {

        Call<ArticleResponse> apiCall = ArticleRequest.initApiCall();

        try {

            Response<ArticleResponse> response = apiCall.execute();

            articles[0] = response.body().getArticles();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // parseArticleContent article content from url

        InputStream in = null;

        for (Article article : articles[0]) {
            try {
                // get HTML from URL
                in = new URL(article.getUrl()).openStream();
                String html = new Scanner(in, "UTF-8").useDelimiter("\\A").next();

                // split HTML to get article content
                String[] splitHeader = html.split(START_DELIMITER);
                String[] splitFooter = splitHeader[1].split(END_DELIMITER);

                // remove HTML tags
                String result = Html.fromHtml(splitFooter[0]).toString();

                article.setContent(result);

            } catch (Exception e) {
                e.printStackTrace();
                article.setContent("Parsiranje sadržaja nije uspjelo.");
            }
        }
        try {
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return articles[0];
    }

    @Override
    protected void onPostExecute(ArrayList<Article> result) {
        super.onPostExecute(result);

        remoteInteractor.setArticles(result);

    }
}