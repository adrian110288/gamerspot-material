package com.adrianlesniak.gamerspot.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Adrian on 23-Aug-14.
 */
public class GamerSpotTextView extends TextView {

    public GamerSpotTextView(Context context) {
        super(context);
        init(context);
    }

    public GamerSpotTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GamerSpotTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context c) {
        setTypeface(Typeface.createFromAsset(c.getAssets(), "fonts/GeosansLight.ttf"));
    }
}
