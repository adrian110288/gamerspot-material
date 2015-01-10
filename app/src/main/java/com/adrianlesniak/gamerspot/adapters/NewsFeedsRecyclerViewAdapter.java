package com.adrianlesniak.gamerspot.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.interfaces.OnHeadlineSelectedListener;
import com.adrianlesniak.gamerspot.utilities.NewsFeed;
import com.adrianlesniak.gamerspot.views.FeedViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adrian on 10-Jun-14.
 */
public class NewsFeedsRecyclerViewAdapter extends RecyclerView.Adapter<FeedViewHolder> {

    private Context mContext;
    private ArrayList<NewsFeed> feedsList;
    private OnHeadlineSelectedListener mCallback;

    public NewsFeedsRecyclerViewAdapter(Context contextIn, List<NewsFeed> feedsListIn, OnHeadlineSelectedListener callback) {
        mContext = contextIn;
        feedsList = (ArrayList<NewsFeed>) feedsListIn;
        mCallback = callback;
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_list_item, null);
        FeedViewHolder holder = new FeedViewHolder(mContext, v, mCallback);

        return holder;
    }

    @Override
    public void onBindViewHolder(FeedViewHolder feedViewHolder, int i) {

        feedViewHolder.setItem(feedsList.get(i));
    }

    @Override
    public int getItemCount() {
        return ((feedsList != null) ? feedsList.size() : 0);
    }

}
