package com.practiceandroid.akshat.myapplication.model;

import java.util.List;

/**
 * Created by akshat-3049 on 26/05/18.
 */

public class News {

    public String status;
    public String totalResults;
    public List<NewsDetails> articles;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public List<NewsDetails> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsDetails> articles) {
        this.articles = articles;
    }
}
