package com.pierluigipapeschi.popularmovies2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.pierluigipapeschi.popularmovies2.data.adapter.PosterAdapter;
import com.pierluigipapeschi.popularmovies2.data.FavoritesContract;
import com.pierluigipapeschi.popularmovies2.data.FavoritesProvider;
import com.pierluigipapeschi.popularmovies2.data.result.MoviesDetailsResult;
import com.pierluigipapeschi.popularmovies2.data.model.MoviesModel;
import com.pierluigipapeschi.popularmovies2.data.model.TrailersModel;
import com.pierluigipapeschi.popularmovies2.data.result.TrailersResult;
import com.pierluigipapeschi.popularmovies2.data.MovieEndPointsAPI;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;

public class MainActivity extends AppCompatActivity implements
        PosterAdapter.PosterAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String QUERY_SORT_EXTRA = "querySort";
    public static final String DETAILS_EXTRA = "movieDetails";

    public static List<TrailersResult> trailersResult;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DEFAULT_SORT = "popular";
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    private List<MoviesDetailsResult> mMoviesDetailsResult;
    private RecyclerView mMoviesRecyclerView;
    private PosterAdapter mPosterAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String sortMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortMode = sharedPreferences.getString(getString(R.string.prefs_sort_key), DEFAULT_SORT);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

        mMoviesRecyclerView = (RecyclerView) findViewById(R.id.main_posters_recycler_view);
        mMoviesRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));

        mMoviesRecyclerView.setLayoutManager(mLayoutManager);

       /* mMoviesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = mLayoutManager.getChildCount();
                int totalItemCount = mLayoutManager.getItemCount();
                int firstVisibleItemPosition = mLayoutManager.;

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMoreItems();
                    }
                }
            }
        });*/
        getData();
    }

    public static int calculateNoOfColumns(Context context) {

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 300);
        return noOfColumns;
    }

    public void getData() {

        MovieEndPointsAPI movieEndPointsAPI = MovieEndPointsAPI.Factory.getInstance();

        switch (sortMode) {
            case MovieEndPointsAPI.SORT_RATING:
                movieEndPointsAPI.getTopRated().enqueue(new Callback<MoviesModel>() {

                    @Override
                    public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                        Log.d(LOG_TAG, "URL: " + call.request().url());

                        if (response.isSuccessful()) {
                            mMoviesDetailsResult = response.body().getMoviesDetailsResults();
                            Log.d(LOG_TAG, "Size: " + mMoviesDetailsResult.size());

                            mPosterAdapter = new PosterAdapter(mMoviesDetailsResult, MainActivity.this);
                            mMoviesRecyclerView.setAdapter(mPosterAdapter);
                        } else {
                            try {
                                Log.d(LOG_TAG, "Error: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesModel> call, Throwable t) {

                        Log.d(LOG_TAG, "ERROR " + t.getMessage());
                    }
                });
                break;
            case MovieEndPointsAPI.SORT_POP:
                movieEndPointsAPI.getMostPopular().enqueue(new Callback<MoviesModel>() {

                    @Override
                    public void onResponse(Call<MoviesModel> call, Response<MoviesModel> response) {
                        Log.d(LOG_TAG, "URL: " + call.request().url());

                        if (response.isSuccessful()) {
                            mMoviesDetailsResult = response.body().getMoviesDetailsResults();
                            Log.d(LOG_TAG, "Size: " + mMoviesDetailsResult.size());

                            mPosterAdapter = new PosterAdapter(mMoviesDetailsResult, MainActivity.this);
                            mMoviesRecyclerView.setAdapter(mPosterAdapter);
                        } else {
                            try {
                                Log.d(LOG_TAG, "Error: " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesModel> call, Throwable t) {

                        Log.d(LOG_TAG, "ERROR " + t.getMessage());
                    }
                });
                break;
            case "my_favorites":
                Cursor cursor;
                try {
                    Uri uri = FavoritesContract.BASE_CONTENT_URI.buildUpon()
                            .appendPath(FavoritesContract.PATH_FAVORITES)
                            .build();

                    cursor = getContentResolver().query(uri, null, null, null, null, null);
                    mMoviesDetailsResult = FavoritesProvider.getListFromCursor(cursor);
                    mPosterAdapter = new PosterAdapter(mMoviesDetailsResult, MainActivity.this);
                    mMoviesRecyclerView.setAdapter(mPosterAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                Log.v(LOG_TAG, "No choice at all?");
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d(LOG_TAG, "onStart()");

        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            PREFERENCES_HAVE_BEEN_UPDATED = false;
            getData();
        }

        if (sortMode.equals("my_favorites")) getData();
    }

    /**
     * Click on a poster means showing movie details.
     * @param position
     */
    @Override
    public void onClick(int position) {

        Class destClass = DetailsActivity.class;

        final Intent activityToStart = new Intent(this, destClass);
        activityToStart.putExtra(DETAILS_EXTRA, mMoviesDetailsResult.get(position));
        activityToStart.putExtra(QUERY_SORT_EXTRA, sortMode);

        MovieEndPointsAPI.Factory.getInstance()
                .getTrailers(mMoviesDetailsResult.get(position).getId().toString())
                .enqueue(new Callback<TrailersModel>() {

            @Override
            public void onResponse(Call<TrailersModel> call, Response<TrailersModel> response) {
                Log.d(LOG_TAG, "URL: " + call.request().url());

                if (response.isSuccessful()) {
                    trailersResult = response.body().getTrailersResults();

                    Log.d(LOG_TAG, "Trailers : " + trailersResult.size());

                    startActivity(activityToStart);
                }
            }

            @Override
            public void onFailure(Call<TrailersModel> call, Throwable t) {
                Log.d(LOG_TAG, "Failure on fetching trailers data");
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        sortMode = sharedPreferences.getString(key, "###");

        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }

    /**
     * Method about menu management
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Menu item clicked
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemClickedId = item.getItemId();

        switch (itemClickedId) {
            case R.id.item_settings:
                Intent settingActivity = new Intent(this, SettingsActivity.class);
                startActivity(settingActivity);
                return true;
            case R.id.item_exit:
                finish();
                System.exit(0);
            default:
                Log.w(LOG_TAG, "Something in the menu went wrong...");
        }

        return super.onOptionsItemSelected(item);
    }
}
