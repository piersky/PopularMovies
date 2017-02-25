package com.pierluigipapeschi.popularmovies2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pierluigipapeschi.popularmovies2.data.adapter.TrailerAdapter;
import com.pierluigipapeschi.popularmovies2.data.result.TrailersResult;

import java.util.List;

/**
 * Created by pier on 18/02/17.
 */

public class TrailersFragment extends Fragment {

    private static final String LOG_TAG = TrailersFragment.class.getSimpleName();

    private RecyclerView mTrailersRecyclerView;
    private TrailerAdapter mTrailerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.trailers, container, false);

        mTrailersRecyclerView = (RecyclerView) view.findViewById(R.id.rv_trailers);
        mTrailersRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mTrailersRecyclerView.setLayoutManager(mLayoutManager);

        mTrailerAdapter = new TrailerAdapter(MainActivity.trailersResult);
        mTrailersRecyclerView.setAdapter(mTrailerAdapter);

        String trailersName = "";
        for (TrailersResult tr : MainActivity.trailersResult) trailersName += tr.getName() + "\n";
        Log.v(LOG_TAG, "onCreateView() trailers " + trailersName);
        return view;
    }
}
