package com.practiceandroid.akshat.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by akshat-3049 on 25/05/18.
 */

public class NewsDetails implements Parcelable{

    public String title;
    public String author;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public NewsDetails(Parcel in){
        String[] data = new String[6];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.title = data[0];
        this.author = data[1];
        this.description = data[2];
        this.url = data[3];
        this.urlToImage = data[4];
        this.publishedAt = data[5];
    }


    public static final Creator CREATOR = new Creator() {
        public NewsDetails createFromParcel(Parcel in) {
            return new NewsDetails(in);
        }

        public NewsDetails[] newArray(int size) {
            return new NewsDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.title,
                this.author,
                this.description,
                this.url,
                this.urlToImage,
                this.publishedAt});
    }
}

