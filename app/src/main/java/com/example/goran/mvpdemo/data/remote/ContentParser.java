package com.example.goran.mvpdemo.data.remote;

import android.text.Html;

import com.example.goran.mvpdemo.data.model.Article;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Goran on 4.12.2017..
 */

public class ContentParser {

    private static final String START_DELIMITER = "introduction\">";
    private static final String END_DELIMITER = "Related Topics<";

    public static ArrayList<Article> parse(ArrayList<Article> articles) {

        OkHttpClient client = new OkHttpClient();

        // parse article content from url
        for (Article article : articles) {

            try {
                // get HTML from URL
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
        return articles;
    }
}
