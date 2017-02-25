package com.pierluigipapeschi.popularmovies2.data.result;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pier on 21/02/17.
 */

public class ReviewsResult implements Parcelable {

    private String author;
    private String content;
    private String urlReview;


    protected ReviewsResult(Parcel in) {

        author = in.readString();
        content = in.readString();
        urlReview = in.readString();
    }

    public static final Creator<ReviewsResult> CREATOR = new Creator<ReviewsResult>() {
        @Override
        public ReviewsResult createFromParcel(Parcel in) {
            return new ReviewsResult(in);
        }

        @Override
        public ReviewsResult[] newArray(int size) {
            return new ReviewsResult[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(author);
        dest.writeString(content);
        dest.writeString(urlReview);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUrlReview(String urlReview) {
        this.urlReview = urlReview;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrlReview() {
        return urlReview;
    }
}
