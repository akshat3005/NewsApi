package com.practiceandroid.akshat.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.practiceandroid.akshat.myapplication.model.NewsDetails;
import com.practiceandroid.akshat.myapplication.util.NetworkUtil;

/**
 * Created by User on 1/2/2018.
 */

public class DescriptiveNewsActivity extends AppCompatActivity {

    NewsDetails news;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descriptive_news);

        if(!NetworkUtil.isNetworkAvailable(getApplicationContext())) {
            finish();
        }

        getIncomingIntent();
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("news")){

            news = getIntent().getExtras().getParcelable("news");
            setNews();
        }
        else {
            Toast.makeText(getApplicationContext(),"Something went Wrong...Please try again later",Toast.LENGTH_SHORT).show();

        }
    }


    private void setNews(){

        TextView title = findViewById(R.id.news_title);
        title.setText(news.getTitle());

        TextView author = findViewById(R.id.news_author);
        author.setText(news.getAuthor());

//        TextView url = findViewById(R.id.news_url);
//        author.setText(news.getUrl());

        TextView date = findViewById(R.id.news_date);
        date.setText(news.getPublishedAt().replace("T"," - ").replace("Z"," "));

        TextView description = findViewById(R.id.news_description);
        description.setText(news.getDescription());

        ImageView image = findViewById(R.id.news_image);
        Glide.with(this)
                .load(news.getUrlToImage())
                .into(image);
    }

}


















