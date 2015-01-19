package com.adrianlesniak.gamerspot.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.utilities.Utils;
import com.squareup.picasso.Callback;

/**
 * Created by Adrian on 06-Jan-15.
 */
public class FeedImage extends RelativeLayout {

    private static final String BACKGROUND_COLOR = "#80000000";
    private static final String FAKEBAR_COLOR = "#18FFFF";
    private static final int MIN_HEIGHT = 200;
    private static final int REVEAL_DURATION = 600;
    private static final int FAKEBAR_HEIGHT_IN_DP = 4;
    public int mImageHeight = 0;
    public int mImageWidth = 0;
    private Context mContext;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private ImageView mErrorImage;
    private Space mSpace;
    private Drawable mImageDrawable;
    private View mFakeProgressBar;
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
        mErrorImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_problem));
        setLayoutParams();
        setBackgroundColor(Color.parseColor(BACKGROUND_COLOR));
        setMinimumHeight(Utils.convertDpToPx(mContext, MIN_HEIGHT));
        instantiateFakeProgressBar();
    }

    private void instantiateFakeProgressBar() {
        mFakeProgressBar = new View(mContext);
//        mFakeProgressBar.setBackgroundColor(Color.parseColor(FAKEBAR_COLOR));
        mFakeProgressBar.setBackgroundResource(R.drawable.glow);
        this.addView(mFakeProgressBar, 1);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utils.convertDpToPx(mContext, 1), Utils.convertDpToPx(mContext, FAKEBAR_HEIGHT_IN_DP));
        params.addRule(ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        params.addRule(ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        mFakeProgressBar.setLayoutParams(params);
    }

    public void setSpaceForScrollView(Space spaceIn) {
        mSpace = spaceIn;
        mSpace.setOnClickListener(new ErrorImageListener());
    }

    private void setImageViewLayoutParams() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(params);
        mImageView.setAdjustViewBounds(true);
        mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    public void setImageFromUrl(String url) {
        mUrl = url;
        Utils.getPicassoInstance(mContext).with(mContext).load(mUrl).into(mImageView, new DownloadCallback());
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

    private class DownloadCallback implements Callback {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onSuccess() {
            if (mErrorImage.getVisibility() == VISIBLE) removeView(mErrorImage);
            new ImageRevealThread().run();
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

    private class ImageRevealThread extends Thread {

        @Override
        public void run() {
            mImageView.setVisibility(INVISIBLE);
            FeedImage.super.addView(mImageView, 0);
            mImageHeight = FeedImage.this.getMeasuredHeight();
            mImageWidth = FeedImage.this.getMeasuredWidth();
            setSpaceHeight();
            removeView(mProgressBar);
            revealImage();
        }

        private void revealImage() {

            AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
            alphaAnimation.setDuration(REVEAL_DURATION - 300);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    startFakeProgress();
                    mImageView.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mImageView.startAnimation(alphaAnimation);

//            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
//
//                    Animator anim = ViewAnimationUtils.createCircularReveal(mImageView,
//                            mImageWidth / 2,
//                            mImageHeight / 2,
//                            0,
//                            mImageWidth * 2);
//
//                    anim.setDuration(REVEAL_DURATION);
//                    anim.addListener(new AnimatorListenerAdapter() {
//
//                        @SuppressLint("NewApi")
//                        @Override
//                        public void onAnimationStart(Animator animation) {
//                            super.onAnimationStart(animation);
//                            startFakeProgress();
//                            mImageView.setVisibility(VISIBLE);
//                        }
//                    });
//
//                    anim.start();
//
//           } else {
//                mImageView.setVisibility(VISIBLE);
//            }
        }

        private void startFakeProgress() {

            ScaleAnimation scaleAnimation = new ScaleAnimation(1, FeedImage.this.getRight(), 1f, 1f, Animation.RELATIVE_TO_PARENT, Animation.RELATIVE_TO_SELF);
            scaleAnimation.setDuration(REVEAL_DURATION / 3);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    FeedImage.this.removeView(mFakeProgressBar);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            mFakeProgressBar.startAnimation(scaleAnimation);
        }

        private void setSpaceHeight() {

            if (mSpace != null) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mSpace.getLayoutParams();
                params.height = mImageHeight;
                mSpace.setLayoutParams(params);
            }
        }
    }
}