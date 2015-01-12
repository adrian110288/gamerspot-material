package com.adrianlesniak.gamerspot.views;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.melnykov.fab.ObservableScrollView;

/**
 * Created by adrian110288 on 10/01/2015.
 */
public class ParallaxScrollView extends ObservableScrollView {

    private static final float PARALLAX_SPEED = 0.6f;
    private View mParallaxingView;
    private RelativeLayout.LayoutParams params;

    public ParallaxScrollView(Context context) {
        super(context);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void attachViewForParallax(View viewIn) {
        mParallaxingView = viewIn;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            int offset = (int) (mParallaxingView.getTop() - (t * PARALLAX_SPEED / 2));
            mParallaxingView.setY(offset);
        } else {

            // TODO Needs testing on API11 device (emulator)

//            if(params == null) {
//                params = (RelativeLayout.LayoutParams) mParallaxingView.getLayoutParams();
//            }
//            int offset = (int) (params.topMargin - (t * PARALLAX_SPEED/2));
//            params.topMargin = offset;
//            mParallaxingView.setLayoutParams(params);
        }

    }
}