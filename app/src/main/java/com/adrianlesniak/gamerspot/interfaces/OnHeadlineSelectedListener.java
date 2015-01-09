package com.adrianlesniak.gamerspot.interfaces;

import com.adrianlesniak.gamerspot.utilities.NewsFeed;

/**
 * Created by Adrian on 17-Aug-14.
 */
public interface OnHeadlineSelectedListener {
    public void onArticleSelected(NewsFeed feed, boolean isSearched);
}