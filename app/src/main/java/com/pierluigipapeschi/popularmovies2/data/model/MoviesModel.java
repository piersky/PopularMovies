package com.pierluigipapeschi.popularmovies2.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.pierluigipapeschi.popularmovies2.data.result.MoviesDetailsResult;

import java.util.List;

/**
 * Created by pier on 17/02/17.
 */

public class MoviesModel implements Parcelable{

    @SerializedName("page")
    private Integer page;

    @SerializedName("results")
    private List<MoviesDetailsResult> moviesDetailsResults = null;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    public final static Parcelable.Creator<MoviesModel> CREATOR = new Creator<MoviesModel>() {

        public MoviesModel createFromParcel(Parcel in) {

            MoviesModel instance = new MoviesModel();

            instance.page = in.readInt();
            in.readList(instance.moviesDetailsResults, (MoviesDetailsResult.class.getClassLoader()));
            instance.totalResults = in.readInt();
            instance.totalPages = in.readInt();

            return instance;
        }

        public MoviesModel[] newArray(int size) {

            return (new MoviesModel[size]);
        }

    };

    public MoviesModel() {
    }

    public MoviesModel(Integer page, List<MoviesDetailsResult> moviesDetailsResults, Integer totalResults, Integer totalPages) {
        super();
        this.page = page;
        this.moviesDetailsResults = moviesDetailsResults;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MoviesDetailsResult> getMoviesDetailsResults() {
        return moviesDetailsResults;
    }

    public void setMoviesDetailsResults(List<MoviesDetailsResult> moviesDetailsResults) {
        this.moviesDetailsResults = moviesDetailsResults;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(moviesDetailsResults);
        dest.writeValue(totalResults);
        dest.writeValue(totalPages);
    }

    public int describeContents() {
        return  0;
    }
}
