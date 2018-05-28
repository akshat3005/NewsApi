package com.practiceandroid.akshat.myapplication.remote;

import com.practiceandroid.akshat.myapplication.model.News;
import com.practiceandroid.akshat.myapplication.model.NewsDetails;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akshat-3049 on 27/05/18.
 */

public class RetrofitClass {

    public static final String BASE_URL = "https://newsapi.org/";
    private static List<NewsDetails> newsList;
    private static final String API_KEY = "5902c655f7f24bbb836ac32c24429bff";


    private static Retrofit getInstance() {

        //To analyze the request, response...
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        okhttpClientBuilder.addInterceptor(httpLoggingInterceptor);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //new Retrofit instance
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClientBuilder.build())
                .build();
    }

    public static NewsApiClient getNewsApiClient() {
        return getInstance().create(NewsApiClient.class);
    }


    public static Call<News> getNews(Map map){

        return getNewsApiClient().getNews(map);
    }

    public static Call<News> getAllNews(Map map){

        return getNewsApiClient().getAllNews(map);
    }


}
