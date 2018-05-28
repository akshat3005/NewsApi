package com.practiceandroid.akshat.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.practiceandroid.akshat.myapplication.DescriptiveNewsActivity;
import com.practiceandroid.akshat.myapplication.R;
import com.practiceandroid.akshat.myapplication.model.NewsDetails;

import java.util.List;
import java.util.Objects;

/**
 * Created by akshat-3049 on 25/05/18.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    List<NewsDetails> newsList;

    public NewsAdapter(Context context, List<NewsDetails> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
             View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item_layout, parent, false);

        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, final int position) {

        final NewsDetails item = newsList.get(position);
        holder.title.setText(item.getTitle());
        if(Objects.equals(item.getAuthor(), null)) {
            holder.author.setText("Source : " + item.getUrl());
        }
        else {
            holder.author.setText("Source : " + item.getAuthor());
        }
        holder.date.setText(item.getPublishedAt().replace("T"," - ").replace("Z"," "));

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DescriptiveNewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("news",newsList.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

            Glide.with(context)
                    .load(item.getUrlToImage())
                    .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void removeItem(int position) {
        newsList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(NewsDetails item, int position) {
        newsList.add(position, item);
        notifyItemInserted(position);
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title, author, date;
        public RelativeLayout viewBackground;
        public CardView viewForeground;

        public NewsViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            author = itemView.findViewById(R.id.author);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.image);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

        }

    }

}
