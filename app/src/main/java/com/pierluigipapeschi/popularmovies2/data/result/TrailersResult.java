package com.pierluigipapeschi.popularmovies2.data.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.pierluigipapeschi.popularmovies2.data.model.TrailersModel;

/**
 * Created by pier on 18/02/17.
 */

public class TrailersResult implements Parcelable {

    public String key;
    public String name;

    public static final Parcelable.Creator<TrailersModel> CREATOR =
            new Parcelable.Creator<TrailersModel>() {

                @Override
                public TrailersModel createFromParcel(Parcel input){
                    return new TrailersModel(input);
                }

                @Override
                public TrailersModel[] newArray(int size) {
                    return new TrailersModel[size];
                }
            };

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(key);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
