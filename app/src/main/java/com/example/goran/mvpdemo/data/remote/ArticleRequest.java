package com.example.goran.mvpdemo.data.remote;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Goran on 8.11.2017..
 */

public class ArticleRequest {

    private static final String BASE_URL = "https://newsapi.org/v1/";
    private static final String API_KEY = "6946d0c07a1c4555a4186bfcade76398";
    private static final String SORT_BY = "top";
    private static final String SOURCE = "bbc-news";

    private static final int CACHE_DURATION = 300; // 5min


    public static Call<ArticleResponse> initApiCall() {

        // set up cache and request

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        request = request.newBuilder()
                                .header("Cache-control", "public, max-age=" + CACHE_DURATION)
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        // set up retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // return call
        return retrofit
                .create(NewsApi.class)
                .getArticleInfo(API_KEY, SORT_BY, SOURCE);

    }
}
