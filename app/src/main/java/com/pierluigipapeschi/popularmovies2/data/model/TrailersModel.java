package com.pierluigipapeschi.popularmovies2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.pierluigipapeschi.popularmovies2.data.result.TrailersResult;

import java.util.List;

/**
 * Created by pier on 18/02/17.
 */

public class TrailersModel implements Parcelable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("results")
    private List<TrailersResult> trailersResults = null;


    public TrailersModel(Parcel in) {

        id = in.readInt();
        in.readList(new TrailersModel().trailersResults, (TrailersResult.class.getClassLoader()));
    }

    public TrailersModel() {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeList(trailersResults);
    }

    public static final Parcelable.Creator<TrailersModel> CREATOR =
            new Parcelable.Creator<TrailersModel>() {

                @Override
                public TrailersModel createFromParcel(Parcel source) {
                    return new TrailersModel(source);
                }

                @Override
                public TrailersModel[] newArray(int size) {
                    return new TrailersModel[size];
                }
            };

    public void setId(Integer id) { this.id = id; }

    public void setTrailersResults(List<TrailersResult> trailersResults) { this.trailersResults = trailersResults; }

    public Integer getId() { return id; }

    public List<TrailersResult> getTrailersResults() { return trailersResults; }

    @Override
    public int describeContents() {
        return 0;
    }
}
