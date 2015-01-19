package com.adrianlesniak.gamerspot.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by adrian110288 on 19/01/2015.
 */
public class GamerspotRecyclerView extends RecyclerView {

    public GamerspotRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext()));
        addItemDecoration(new FeedListSeparator(getContext()));
    }
}
