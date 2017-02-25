package com.pierluigipapeschi.popularmovies2.data.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pierluigipapeschi.popularmovies2.R;
import com.pierluigipapeschi.popularmovies2.data.result.TrailersResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pier on 18/02/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();

    private List<TrailersResult> mTrailersResult;


    public TrailerAdapter(List<TrailersResult> trailersList) {

        mTrailersResult = trailersList;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(inflater.inflate(R.layout.trailer_item, parent, false));

        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        String text = mTrailersResult.get(position).getName();
        holder.trailerName.setText(text);
    }

    @Override
    public int getItemCount() {

        if (mTrailersResult != null) {
            return mTrailersResult.size();
        }
        return 0;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView_name)
        TextView trailerName;
        @BindView(R.id.imageView_play)
        ImageView mPlayTrailer;

        public TrailerViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

            Picasso.with(itemView.getContext())
                    .load(R.drawable.youtube_icon_50)
                    .into(mPlayTrailer);
        }

        @OnClick(R.id.imageView_play)
        public void clickedTrailer(View view) {

            Uri builtUri = Uri.parse("https://www.youtube.com/watch").buildUpon()
                    .appendQueryParameter("v", mTrailersResult.get(getAdapterPosition()).getKey())
                    .build();

            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, builtUri);

            if (youtubeIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
                view.getContext().startActivity(youtubeIntent);
            }
        }
    }
}
