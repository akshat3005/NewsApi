package com.practiceandroid.akshat.myapplication.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akshat-3049 on 28/05/18.
 */

public class MapUtil {

    private Map<String,String> queryMap;
    private final String API_KEY = "5902c655f7f24bbb836ac32c24429bff";

    //Everything
    public MapUtil(String q, String sources, String domains, String from, String to, String language, String sortBy, String pageSize, String page) {
        queryMap = new HashMap<>();
        if(!q.equals("")) {
            queryMap.put("q",q);
        }
        if(!sources.equals("")) {
            queryMap.put("sources",sources);
        }
        if(!sortBy.equals("")) {
            queryMap.put("sortBy",sortBy);

        }
        if(!pageSize.equals("")) {
            queryMap.put("pageSize",pageSize);

        }
        if(!page.equals("")) {
            queryMap.put("page",page);

        }
        if(!domains.equals("")) {
            queryMap.put("domains",domains);

        }
        if(!from.equals("")) {
            queryMap.put("from",from);

        }
        if(!to.equals("")) {
            queryMap.put("to",to);

        }
        if(!language.equals("")) {
            queryMap.put("language",language);

        }
        queryMap.put("apiKey",API_KEY);

    }

    //Top-Headlines
    public MapUtil(String q, String sources, String country, String category, String pageSize, String page) {
        queryMap = new HashMap<>();
        if(!q.equals("")) {
            queryMap.put("q",q);
        }
        if(!sources.equals("")) {
            queryMap.put("sources",sources);
        }
        if(!country.equals("")) {
            queryMap.put("country",country);

        }
        if(!pageSize.equals("")) {
            queryMap.put("pageSize",pageSize);

        }
        if(!page.equals("")) {
            queryMap.put("page",page);

        }
        if(!category.equals("")) {
            queryMap.put("category",category);

        }
        queryMap.put("apiKey",API_KEY);
    }

    public Map<String,String> getQueryMap() {
        return queryMap;
    }
}
