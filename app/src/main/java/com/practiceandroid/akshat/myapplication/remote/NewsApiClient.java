package com.practiceandroid.akshat.myapplication.remote;

import com.practiceandroid.akshat.myapplication.model.News;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by akshat-3049 on 26/05/18.
 */

public interface NewsApiClient {

    @GET("/v2/top-headlines")
    Call<News> getNews(@QueryMap Map<String, String> filters);

    @GET("/v2/everything")
    Call<News> getAllNews(@QueryMap Map<String, String> filters);


}
