package com.adrianlesniak.gamerspot.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.melnykov.fab.FloatingActionButton;

/**
 * Created by Adrian on 10-Jan-15.
 */
public class ClickableFloatingActionButton extends FloatingActionButton implements View.OnClickListener {

    public ClickableFloatingActionButton(Context context) {
        super(context);
        init();
    }

    public ClickableFloatingActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i("FAB_LOG", "FAB CLICKED");
    }
}
