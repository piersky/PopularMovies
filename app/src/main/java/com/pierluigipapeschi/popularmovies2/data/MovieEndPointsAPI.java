package com.pierluigipapeschi.popularmovies2.data;

import com.pierluigipapeschi.popularmovies2.BuildConfig;
import com.pierluigipapeschi.popularmovies2.data.model.MoviesModel;
import com.pierluigipapeschi.popularmovies2.data.model.ReviewsModel;
import com.pierluigipapeschi.popularmovies2.data.model.TrailersModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pier on 16/02/17.
 */

public interface MovieEndPointsAPI {

    String API_KEY      = BuildConfig.TMDB_API_KEY;
    String BASE_URL     = "http://api.themoviedb.org/3/movie/";
    String SORT_RATING  = "top_rated";
    String SORT_POP     = "popular";


    @GET(SORT_RATING + "?api_key=" + API_KEY)
    Call<MoviesModel> getTopRated();

    @GET(SORT_POP + "?api_key=" + API_KEY)
    Call<MoviesModel> getMostPopular();

    @GET("{id}/videos" + "?api_key=" + API_KEY)
    Call<TrailersModel> getTrailers(@Path("id") String id);

    @GET("{id}/reviews" + "?api_key=" + API_KEY)
    Call<ReviewsModel> getReviews(@Path("id") String id);

    class Factory {

        private static MovieEndPointsAPI service;

        public static MovieEndPointsAPI getInstance() {

            if (true) { //(service == null) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                service = retrofit.create(MovieEndPointsAPI.class);
            }

            return service;
        }
    }
}
