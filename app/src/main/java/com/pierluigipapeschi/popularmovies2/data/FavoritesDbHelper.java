package com.pierluigipapeschi.popularmovies2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pier on 14/02/17.
 */

public class FavoritesDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = FavoritesDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "favorites_movies.db";
    private static final int DATABASE_VERSION = 1;

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesContract.FavoritesEntry.TABLE_NAME + " (" +
                FavoritesContract.FavoritesEntry._ID                  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID      + " VARCHAR(20), " +
                FavoritesContract.FavoritesEntry.COLUMN_DATE          + " DATE," +
                FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH   + " VARCHAR(100), " +
                FavoritesContract.FavoritesEntry.COLUMN_BACKDROP_PATH + " VARCHAR(100), " +
                FavoritesContract.FavoritesEntry.COLUMN_RATING        + " DECIMAL(10,1), " +
                FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT    + " INT, " +
                FavoritesContract.FavoritesEntry.COLUMN_TITLE         + " VARCHAR(100), " +
                FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW      + " TEXT, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_NAME    + " VARCHAR(100));";

        Log.d(LOG_TAG, CREATE_FAVORITES_TABLE);
        db.execSQL(CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoritesEntry.TABLE_NAME);
        onCreate(db);
    }
}
