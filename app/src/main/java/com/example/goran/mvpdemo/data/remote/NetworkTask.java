package com.example.goran.mvpdemo.data.remote;

import android.os.AsyncTask;
import android.text.Html;

import com.example.goran.mvpdemo.data.Article;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Goran on 20.11.2017..
 */

public class NetworkTask extends AsyncTask<ArrayList<Article>, Void, ArrayList<Article>> {

    private static final String START_DELIMITER = "introduction\">";
    private static final String END_DELIMITER = "Related Topics<";

    private Listener listener;

    public interface Listener {

        void onTaskComplete(ArrayList<Article> articles);
    }

    public NetworkTask(Listener listener) {
        this.listener = listener;
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
                    String[] splitHeader = urlResponse.body().string().split(START_DELIMITER);
                    String[] splitFooter = splitHeader[1].split(END_DELIMITER);

                    // remove HTML tags
                    article.setContent(Html.fromHtml(splitFooter[0]).toString());

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
            listener.onTaskComplete(result);
        }
    }
}