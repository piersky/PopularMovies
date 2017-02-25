package com.pierluigipapeschi.popularmovies2.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pierluigipapeschi.popularmovies2.R;
import com.pierluigipapeschi.popularmovies2.data.result.MoviesDetailsResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by pier on 18/02/17.
 */

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterViewHolder> {

    private static  final String LOG_TAG = PosterAdapter.class.getSimpleName();

    private PosterAdapterOnClickHandler mClickHandler;
    private List<MoviesDetailsResult> mMovieDetailsList;


    public interface PosterAdapterOnClickHandler {

        void onClick(int position);
    }


    public class PosterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView mPosterIV;

        public PosterViewHolder(View v) {

            super(v);
            mPosterIV = (ImageView) v.findViewById(R.id.poster_main_image_view);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mClickHandler.onClick(getAdapterPosition());
        }
    }


    public PosterAdapter(List<MoviesDetailsResult> moviesList, PosterAdapterOnClickHandler clickHandler) {

        mMovieDetailsList = moviesList;
        mClickHandler = clickHandler;
    }

    @Override
    public PosterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new PosterViewHolder(inflater.inflate(R.layout.poster_main, parent, false));
    }

    @Override
    public void onBindViewHolder(PosterViewHolder holder, int position) {

        String imageUrl = "http://image.tmdb.org/t/p/w300" + mMovieDetailsList.get(position).getPosterPath();

        Picasso.with(holder.mPosterIV.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_tmdb)
                .error(R.drawable.error)
                .into(holder.mPosterIV);
    }

    @Override
    public int getItemCount() {

        if (mMovieDetailsList == null) {
            Log.d(LOG_TAG, "Count = 0");
            return 0;
        }

        return mMovieDetailsList.size();
    }
}
