package com.pierluigipapeschi.popularmovies2;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pierluigipapeschi.popularmovies2.data.FavoritesContract;
import com.pierluigipapeschi.popularmovies2.data.result.MoviesDetailsResult;
import com.pierluigipapeschi.popularmovies2.data.model.ReviewsModel;
import com.pierluigipapeschi.popularmovies2.data.result.ReviewsResult;
import com.pierluigipapeschi.popularmovies2.data.MovieEndPointsAPI;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pier on 18/02/17.
 */

public class DetailsActivity extends FragmentActivity {

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    private MoviesDetailsResult mDetails;

    public static List<ReviewsResult> mReviewsList;
    public static String movieId;

    @BindView(R.id.tv_details_title)        TextView  mTitleTV;
    @BindView(R.id.tv_details_year)         TextView  mYearTV;
    @BindView(R.id.iv_details_poster)       ImageView mPosterIV;
    @BindView(R.id.tv_details_vote_count)   TextView  mVoteCountTV;
    @BindView(R.id.add_favorite_button)     Button    mButtonAdd;
    @BindView(R.id.tv_synopsis_intro)       TextView  mSynopsisIntro;
    @BindView(R.id.tv_details_synopsis)     TextView  mSynopsis;
    @BindView(R.id.ratingBar)               RatingBar mRatingBar;
    @BindView(R.id.button_show_reviews)     Button    mButtonShowReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.DETAILS_EXTRA))
                mDetails = intent.getParcelableExtra(MainActivity.DETAILS_EXTRA);

        populateDetails();
    }

    private void populateDetails() {

        mTitleTV.setText(mDetails.getTitle());

        SimpleDateFormat dateToParse = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parseDate = dateToParse.parse(mDetails.getReleaseDate());
            SimpleDateFormat newFormat = new SimpleDateFormat("y");
            mYearTV.setText(newFormat.format(parseDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.with(this)
                .load("http://image.tmdb.org/t/p/w300" + mDetails.getPosterPath())
                .placeholder(R.drawable.placeholder_tmdb)
                .error(R.drawable.error)
                .into(mPosterIV);

        //LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
        //stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        mRatingBar.setStepSize(0.2f);
        float rat = (float) (mDetails.getVoteAverage()/2.0f);
        mRatingBar.setRating(rat);

        mVoteCountTV.setText("(" + mDetails.getVoteCount() + ")");

        mSynopsisIntro.setText(R.string.details_overview);
        mSynopsis.setText(mDetails.getOverview());

        movieId = mDetails.getId().toString();

        changeButtonText();
    }

    @OnClick(R.id.add_favorite_button)
    public void addFavorites(View view) {

        Cursor cursor = getCursorFromContentProvider();
        //Not present in the collection, adding
        if (cursor.getCount() <= 0) {
            Uri uri = getContentResolver().insert(FavoritesContract.FavoritesEntry.CONTENT_URI, mDetails.toContentValues());
            if (uri != null) {
                changeButtonText();
                Toast.makeText(this, getResources().getString(R.string.details_added_to_favorites), Toast.LENGTH_SHORT).show();
            }
        }
        //Movie present in the collection, removing
        else {
            Uri uriDel = FavoritesContract.FavoritesEntry.CONTENT_URI.buildUpon().appendPath(movieId).build();
            int d = getContentResolver().delete(uriDel, "movie_id=?", new String[]{movieId});

            if (d > 0) {
                changeButtonText();
                Toast.makeText(this, getResources().getString(R.string.details_removed_from_favorites), Toast.LENGTH_SHORT).show();
            }
            else {
                Log.d(LOG_TAG, "No rows deleted");
            }
        }
    }

    private void changeButtonText() {

        Cursor cursor = getCursorFromContentProvider();
        if (cursor.getCount() > 0) {
            Log.v(LOG_TAG, "Found # " + cursor.getCount());
            mButtonAdd.setText(getResources().getString(R.string.details_button_remove));
        } else {
            Log.d(LOG_TAG, "Movie not present in the collection");
            mButtonAdd.setText(getResources().getString(R.string.details_button_add));
        }
    }

    private Cursor getCursorFromContentProvider() {

        Cursor cursor = null;
        try {
            Uri uri = FavoritesContract.BASE_CONTENT_URI.buildUpon()
                    .appendPath(FavoritesContract.PATH_FAVORITES)
                    .appendPath(movieId)
                    .build();

            cursor = getContentResolver().query(uri,
                    null,
                    FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + "=" + movieId,
                    null,
                    null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    @OnClick(R.id.button_show_reviews)
    public void showReviews(final View view) {

        Log.d(LOG_TAG, "showReviews()");

        MovieEndPointsAPI movieEndPointsAPI = MovieEndPointsAPI.Factory.getInstance();
        movieEndPointsAPI.getReviews(movieId).enqueue(new Callback<ReviewsModel>() {

            @Override
            public void onResponse(Call<ReviewsModel> call, Response<ReviewsModel> response) {
                Log.d(LOG_TAG, "URL: " + call.request().url());

                if (response.isSuccessful()) {
                    mReviewsList = response.body().getReviewsResult();

                    if (mReviewsList.size() > 0) {
                        Class destClass = ReviewsActivity.class;
                        final Intent intent = new Intent(view.getContext(), destClass);
                        //intent.putExtra(MainActivity.DETAILS_EXTRA, mDetails);
                        startActivity(intent);
                    } else
                        Toast.makeText(view.getContext(), R.string.details_no_reviews_found, Toast.LENGTH_LONG).show();

                } else {
                    try {
                        Log.e(LOG_TAG, "Error: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewsModel> call, Throwable t) {
                Log.w(LOG_TAG, "Error fetching reviews");
            }
        });
    }
}
