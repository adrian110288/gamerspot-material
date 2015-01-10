package com.adrianlesniak.gamerspot.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.utilities.CommonUtilities;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Adrian on 06-Jan-15.
 */
public class FeedImage extends RelativeLayout {

    private static final String BACKGROUND_COLOR = "#80000000";
    private static final int MIN_HEIGHT = 160;
    public int imageHeight = 0;
    private Context mContext;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private ImageView mErrorImage;
    private String mUrl;

    public FeedImage(Context context) {
        super(context);
        init(context);
    }

    public FeedImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mProgressBar = new ProgressBar(mContext);
        addViewInCenter(mProgressBar);
        mImageView = new ImageView(mContext);
        setImageViewLayoutParams();
        mErrorImage = new ImageView(mContext);
        mErrorImage.setOnClickListener(new ErrorImageListener());
        mErrorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_problem));
        setLayoutParams();
        setBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
        setMinimumHeight(CommonUtilities.convertDpToPx(MIN_HEIGHT));
    }

    private void setImageViewLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(params);
        mImageView.setAdjustViewBounds(true);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void setImageFromUrl(String url) {
        mUrl = url;
        Picasso.with(mContext).load(mUrl).into(mImageView, new DownloadCallback());
    }

    private void setLayoutParams() {
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
    }

    public void addViewInCenter(View child) {
        super.addView(child);
        RelativeLayout.LayoutParams params = (LayoutParams) child.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        child.setLayoutParams(params);
    }

    private void revealImage() {

        final Drawable d = mImageView.getDrawable();
        imageHeight = d.getIntrinsicHeight();

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {


            mImageView.addOnLayoutChangeListener(new OnLayoutChangeListener() {

                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    Animator anim = ViewAnimationUtils.createCircularReveal(mImageView,
                            d.getIntrinsicWidth() / 2,
                            d.getIntrinsicHeight() / 2,
                            0,
                            d.getIntrinsicWidth() * 2);

                    anim.setDuration(600);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            super.onAnimationStart(animation);
                            mImageView.setVisibility(VISIBLE);
                        }
                    });

                    anim.start();
                }
            });

        } else {
            mImageView.setVisibility(VISIBLE);
        }
    }

    private class DownloadCallback implements Callback {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSuccess() {

            mImageView.setVisibility(INVISIBLE);
            FeedImage.super.addView(mImageView);
            removeView(mProgressBar);
            revealImage();
        }

        @Override
        public void onError() {
            addViewInCenter(mErrorImage);
            removeView(mProgressBar);
        }
    }

    private class ErrorImageListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            removeView(mErrorImage);
            addView(mProgressBar);
            setImageFromUrl(mUrl);
        }
    }
}
