package com.pierluigipapeschi.popularmovies2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pier on 14/02/17.
 */

public class FavoritesContract {

    private static final String     SCHEME           = "content://";

    public static final String      AUTHORITY        = "com.pierluigipapeschi.popularmovies2";
    public static final Uri         BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);
    public static final String      PATH_FAVORITES   = "favorites";


    public static final class FavoritesEntry implements BaseColumns {

        public static final Uri CONTENT_URI   = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";

        public static final String COLUMN_MOVIE_NAME    = "movie_name";
        public static final String COLUMN_MOVIE_ID      = "movie_id";
        public static final String COLUMN_POSTER_PATH   = "poster_path";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_TITLE         = "title";
        public static final String COLUMN_RATING        = "vote_average";
        public static final String COLUMN_VOTE_COUNT    = "vote_count";
        public static final String COLUMN_DATE          = "release_date";
        public static final String COLUMN_OVERVIEW      = "overview";
    }
}
