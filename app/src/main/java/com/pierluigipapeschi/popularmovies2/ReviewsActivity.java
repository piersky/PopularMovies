package com.pierluigipapeschi.popularmovies2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.pierluigipapeschi.popularmovies2.data.adapter.ReviewsAdapter;
import com.pierluigipapeschi.popularmovies2.data.result.MoviesDetailsResult;

/**
 * Created by pier on 22/02/17.
 */

public class ReviewsActivity extends AppCompatActivity {

    private static final String LOG_TAG = ReviewsActivity.class.getSimpleName();

    private RecyclerView mReviewsRecyclerView;
    private ReviewsAdapter mReviewsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MoviesDetailsResult mDetails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Log.d(LOG_TAG, "onCreate()");

        ActionBar ab = this.getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        mReviewsRecyclerView = (RecyclerView) findViewById(R.id.rv_reviews);
        mReviewsRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mReviewsRecyclerView.setLayoutManager(mLayoutManager);

        mReviewsAdapter = new ReviewsAdapter(DetailsActivity.mReviewsList);
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(LOG_TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(LOG_TAG, "onRestoreInstanceState()");
    }
}
