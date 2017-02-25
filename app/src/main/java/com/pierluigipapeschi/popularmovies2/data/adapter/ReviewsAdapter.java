package com.pierluigipapeschi.popularmovies2.data.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pierluigipapeschi.popularmovies2.R;
import com.pierluigipapeschi.popularmovies2.data.result.ReviewsResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pier on 21/02/17.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private static final String LOG_TAG = ReviewsAdapter.class.getSimpleName();
    private List<ReviewsResult> mReviewsResult;


    public ReviewsAdapter(List<ReviewsResult> list) {

        mReviewsResult = list;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ReviewsViewHolder viewHolder = new ReviewsViewHolder(inflater.inflate(R.layout.review_item, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {

        holder.mAuthorName.setText(mReviewsResult.get(position).getAuthor());
        holder.mComment.setText(mReviewsResult.get(position).getContent());
    }

    @Override
    public int getItemCount() {

        if (mReviewsResult != null) {
            return mReviewsResult.size();
        }
        return 0;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_author_name)
        TextView mAuthorName;
        @BindView(R.id.tv_comment)
        TextView mComment;

        public ReviewsViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
