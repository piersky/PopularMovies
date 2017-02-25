package com.pierluigipapeschi.popularmovies2.data.result;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.pierluigipapeschi.popularmovies2.data.FavoritesContract;

/**
 * Created by pier on 17/02/17.
 */

public class MoviesDetailsResult implements Parcelable {

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("id")
    private Integer id;

    @SerializedName("title")
    private String title;

    @SerializedName("vote_count")
    private Integer voteCount;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("backdrop_path")
    private String backdropPath;


    public final static Parcelable.Creator<MoviesDetailsResult> CREATOR = new Creator<MoviesDetailsResult>() {

        public MoviesDetailsResult createFromParcel(Parcel in) {

            MoviesDetailsResult instance = new MoviesDetailsResult();

            instance.posterPath = in.readString();
            instance.overview = in.readString();
            instance.releaseDate = in.readString();
            instance.id = in.readInt();
            instance.title = in.readString();
            instance.voteCount = in.readInt();
            instance.voteAverage = in.readDouble();
            instance.backdropPath = in.readString();

            return instance;
        }

        public MoviesDetailsResult[] newArray(int size) {

            return (new MoviesDetailsResult[size]);
        }

    };

    public MoviesDetailsResult() {
    }

    public MoviesDetailsResult(String posterPath, String overview, String releaseDate, Integer id,
                               String title, Integer voteCount, Double voteAverage, String backdropPath) {

        super();
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.id = id;
        this.title = title;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getBackdropPath() { return backdropPath; }

    public void setBackdropPath(String backdropPath) { this.backdropPath = backdropPath; }

    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(voteCount);
        dest.writeDouble(voteAverage);
        dest.writeString(backdropPath);
    }

    public int describeContents() {
        return  0;
    }

    public ContentValues toContentValues(){

        ContentValues cv = new ContentValues();

        cv.put(FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH, posterPath);
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW, overview);
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_DATE, releaseDate);
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, id);
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_TITLE, title);
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT, voteCount);
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_RATING, voteAverage);
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_BACKDROP_PATH, backdropPath);

        return cv;
    }
}
