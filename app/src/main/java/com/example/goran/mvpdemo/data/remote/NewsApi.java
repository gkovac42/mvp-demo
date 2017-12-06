package com.example.goran.mvpdemo.data.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Goran on 13.11.2017..
 */

public interface NewsApi {

    @GET("articles")
    Observable<ArticleResponse> getArticleInfo(@Query("apiKey") String apiKey,
                                               @Query("sortBy") String sortBy,
                                               @Query("source") String source);


}
