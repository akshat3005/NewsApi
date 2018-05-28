package com.practiceandroid.akshat.myapplication.ViewController;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.practiceandroid.akshat.myapplication.adapters.NewsAdapter;
import com.practiceandroid.akshat.myapplication.model.News;
import com.practiceandroid.akshat.myapplication.model.NewsDetails;
import com.practiceandroid.akshat.myapplication.remote.RetrofitClass;
import com.practiceandroid.akshat.myapplication.util.DateUtil;
import com.practiceandroid.akshat.myapplication.util.MapUtil;
import com.practiceandroid.akshat.myapplication.util.NetworkUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by akshat-3049 on 28/05/18.
 */

public class ViewControl {

    List<NewsDetails> newsDetailsList;
    NewsAdapter newsAdapter;
    Map<String,String> map;
    EnqueueCall callHandler;
    Context context;

    public ViewControl(Context context, List<NewsDetails> newsDetailsList, NewsAdapter newsAdapter) {
        this.newsDetailsList = newsDetailsList;
        this.newsAdapter = newsAdapter;
        this.map = new HashMap<>();
        this.callHandler = new EnqueueCall(this,context);
        this.context = context;
    }

    public void updateRecyclerView(List<NewsDetails> newsList, boolean isContinuedRequest) {
        if(isContinuedRequest) {
            newsDetailsList.addAll(newsList);
        }
        else {
            newsDetailsList.clear();
            newsDetailsList.addAll(newsList);
        }
        newsAdapter.notifyDataSetChanged();
    }

    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int position, CoordinatorLayout coordinatorLayout) {
        if (viewHolder instanceof NewsAdapter.NewsViewHolder) {
            String name = newsDetailsList.get(viewHolder.getAdapterPosition()).getTitle();

            final NewsDetails deletedItem = newsDetailsList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            newsAdapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    newsAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    public void enqueueTopNewsCall(Map<String,String> queryMap, final boolean isContinuedRequest) {
        map = queryMap;
        Call<News> call = RetrofitClass.getNews(map);
        callHandler.enqueueCall(call, isContinuedRequest);
    }

    public void enqueueEverythingCall(Map<String,String> queryMap, final boolean isContinuedRequest) {
        map = queryMap;
        Call<News> call = RetrofitClass.getAllNews(map);
        callHandler.enqueueCall(call, isContinuedRequest);
    }

    public void enqueueDateCall(final boolean isContinuedRequest) {
        String[] date = DateUtil.datePickerDialog(context);
        Log.d("DATE",date.toString());
        map.put("from",date[0]);
        map.put("to",date[1]);
        enqueueEverythingCall(map, isContinuedRequest);
    }

    public void sortList(String sortType, final boolean isContinuedRequest) {
        Map<String,String > queryMap = new MapUtil("", "google-news-india", "", "", "", "",sortType,"100","").getQueryMap();
        enqueueEverythingCall(queryMap, isContinuedRequest);
    }

    void onSearchQuery(String query) {
        if(!query.equals("")) {
            Map<String,String > queryMap = new MapUtil(query, "", "", "", "", "","","100","").getQueryMap();
            enqueueEverythingCall(queryMap, false);
        }
    }

    public void getCategoryNews(String category) {
        Map<String,String > queryMap = new MapUtil("","","in",category.toLowerCase(),"","").getQueryMap();
        enqueueTopNewsCall(queryMap, false);

    }

    public MaterialSearchView.OnQueryTextListener getSearchViewQueryListener() {
        return new SearchViewController(this);
    }
}
