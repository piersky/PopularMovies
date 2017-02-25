package com.pierluigipapeschi.popularmovies2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.pierluigipapeschi.popularmovies2.data.result.ReviewsResult;

import java.util.List;

/**
 * Created by pier on 21/02/17.
 */

public class ReviewsModel implements Parcelable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("results")
    private List<ReviewsResult> reviewsResult;


    public ReviewsModel() {
    }

    protected ReviewsModel(Parcel in) {

        id = in.readInt();
        in.readList(new ReviewsModel().reviewsResult, (ReviewsResult.class.getClassLoader()));
    }

    public static final Creator<ReviewsModel> CREATOR = new Creator<ReviewsModel>() {
        @Override
        public ReviewsModel createFromParcel(Parcel in) {
            return new ReviewsModel(in);
        }

        @Override
        public ReviewsModel[] newArray(int size) {
            return new ReviewsModel[size];
        }
    };

    public void setId(Integer id) {
        this.id = id;
    }

    public void setReviewsResult(List<ReviewsResult> reviewsResult) {
        this.reviewsResult = reviewsResult;
    }

    public Integer getId() {
        return id;
    }

    public List<ReviewsResult> getReviewsResult() {
        return reviewsResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeList(reviewsResult);
    }
}
