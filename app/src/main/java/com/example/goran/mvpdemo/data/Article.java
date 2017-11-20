package com.example.goran.mvpdemo.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Goran on 8.11.2017..
 */

public class Article implements Parcelable {

    private String title;
    private String url;
    private String urlToImage;
    private String content;

    public Article(String title, String urlToImage, String url) {
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public Article() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    // parcelable constructor
    public Article(Parcel parcel) {

        String[] data = new String[4];

        parcel.readStringArray(data);

        this.title = data[0];
        this.url = data[1];
        this.urlToImage = data[2];
        this.content = data[3];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{title, url, urlToImage, content});
    }

    public static final Creator CREATOR = new Creator() {
        @Override
        public Article createFromParcel(Parcel parcel) {
            return new Article(parcel);
        }

        @Override
        public Article[] newArray(int i) {
            return new Article[i];
        }
    };

}
