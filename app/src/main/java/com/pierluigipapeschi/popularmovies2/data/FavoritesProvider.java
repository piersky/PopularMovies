package com.pierluigipapeschi.popularmovies2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pierluigipapeschi.popularmovies2.data.result.MoviesDetailsResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pier on 14/02/17.
 */

public class FavoritesProvider extends ContentProvider {

    private static final String LOG_TAG = FavoritesProvider.class.getSimpleName();

    private static final int FAVORITES      = 100;
    private static final int FAVORITES_ID   = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.PATH_FAVORITES, FAVORITES);
        uriMatcher.addURI(FavoritesContract.AUTHORITY, FavoritesContract.PATH_FAVORITES + "/#", FAVORITES_ID);

        return uriMatcher;
    }

    private FavoritesDbHelper mFavoritesDbHelper;

    @Override
    public boolean onCreate() {

        Context context = getContext();
        mFavoritesDbHelper = new FavoritesDbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mFavoritesDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;

        switch (match) {
            case FAVORITES:
                cursor = db.query(FavoritesContract.FavoritesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case FAVORITES_ID:
                cursor = db.query(FavoritesContract.FavoritesEntry.TABLE_NAME,
                        null,
                        selection,
                        null,
                        null,
                        null,
                        null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.d(LOG_TAG, "insert(): movie id = " + values.getAsString(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID));

        final SQLiteDatabase db = mFavoritesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch (match) {
            case FAVORITES:
                long id = db.insert(FavoritesContract.FavoritesEntry.TABLE_NAME, null, values);
                if (id > 0) returnUri = ContentUris.withAppendedId(FavoritesContract.FavoritesEntry.CONTENT_URI, id);
                else throw new SQLException("Failed inserting row... " + uri);
                break;
            case FAVORITES_ID:
                Log.d(LOG_TAG, "Unused yet");
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mFavoritesDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int deleted;

        if (match == FAVORITES_ID) {
            String id = uri.getPathSegments().get(1);
            Log.d(LOG_TAG, "Segment id: " + id);
            deleted = db.delete(FavoritesContract.FavoritesEntry.TABLE_NAME, selection, selectionArgs);
        } else throw new UnsupportedOperationException("Unknown URI: " + uri);

        if (deleted != 0) {
            Log.d(LOG_TAG, "Movie correctly deleted");
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static List<MoviesDetailsResult> getListFromCursor(Cursor cursor) {

        Log.d(LOG_TAG, "getListFromCursor()");

        List<MoviesDetailsResult> detailsResults = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                MoviesDetailsResult detail = new MoviesDetailsResult(
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_POSTER_PATH)),
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_OVERVIEW)),
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_DATE)),
                        cursor.getInt(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID)),
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_TITLE)),
                        cursor.getInt(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_VOTE_COUNT)),
                        cursor.getDouble(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_RATING)),
                        cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_BACKDROP_PATH))
                        );
                detailsResults.add(detail);
            } while (cursor.moveToNext());
        }

        return detailsResults;
    }
}
