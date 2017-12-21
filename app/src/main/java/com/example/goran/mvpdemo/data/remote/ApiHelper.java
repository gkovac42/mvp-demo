package com.example.goran.mvpdemo.data.remote;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by Goran on 8.11.2017..
 */

@Singleton
public class ApiHelper {

    public static final String BASE_URL = "https://newsapi.org/v1/";
    private static final String API_KEY = "6946d0c07a1c4555a4186bfcade76398";
    private static final String SORT_BY = "top";
    private static final String SOURCE = "bbc-news";

    private NewsApi newsApi;

    @Inject
    public ApiHelper(NewsApi newsApi) {
        this.newsApi = newsApi;
    }

    public Observable<ArticleResponse> getDataObservable() {
        return  newsApi.getArticleInfo(API_KEY, SORT_BY, SOURCE);

    }
}
