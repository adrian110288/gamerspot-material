package com.adrianlesniak.gamerspot.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.database.DAO;
import com.adrianlesniak.gamerspot.interfaces.FullArticleClickListener;
import com.adrianlesniak.gamerspot.utilities.CommonUtilities;
import com.adrianlesniak.gamerspot.utilities.NewsFeed;
import com.adrianlesniak.gamerspot.views.FeedImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Adrian on 13-Jun-14.
 */
public class NewsDetailsFragment extends Fragment implements FullArticleClickListener {

    private static List<String> urlList;
    @InjectView(R.id.feed_main_image)
    FeedImage mFeedMainImage;
    @InjectView(R.id.details_title)
    TextView titleView;
    @InjectView(R.id.details_creator)
    TextView creatorView;
    @InjectView(R.id.details_date)
    TextView dateView;
    @InjectView(R.id.details_description)
    TextView descriptionView;
    @InjectView(R.id.details_desc_layout)
    LinearLayout descriptionLinearLayout;
    @InjectView(R.id.details_header_layout)
    RelativeLayout mHeaderLayout;

    //    @InjectView(R.id.button_full_article) GamerSpotButton fullArticleButton;
    @InjectView(R.id.layout_scroll_view)
    ScrollView mScrollView;
    private NewsFeed feed;
    private int descriptionLayoutWidth = 0;

    private ViewTreeObserver viewTreeObserver;
    private DAO dao;
    private boolean isArticleFavourite = false;
    private boolean isSearched;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        feed = (NewsFeed) getArguments().get("FEED");
//        isSearched = getArguments().getBoolean("searched");
        dao = CommonUtilities.getDatabaseAccessor();
        getImagesUrl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_details, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (urlList.size() != 0) {
            mFeedMainImage.setImageFromUrl(urlList.get(0));
            setScrollViewOffset();
        } else {
            mFeedMainImage.setVisibility(View.GONE);
        }

        mHeaderLayout.setBackgroundColor(getResources().getColor(R.color.primaryDark));
        titleView.setText(feed.getTitle());
        titleView.setTextColor(getResources().getColor(android.R.color.white));
        creatorView.setText(feed.getCreator());
        dateView.setText(CommonUtilities.getFormattedDate(feed.getDate()));
        descriptionView.setText(Html.fromHtml(feed.getDescription().replaceAll("<img.+?>", "")));

//        viewTreeObserver = descriptionLinearLayout.getViewTreeObserver();
//
//        viewTreeObserver.addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
//
//            @Override
//            @TargetApi(16)
//            public void onGlobalLayout() {
//
//                descriptionLayoutWidth = descriptionLinearLayout.getWidth();
//
//                if (urlList.size() != 0 && CommonUtilities.isOnline()) {
//                    getActivity().setProgressBarIndeterminateVisibility(true);
//                    new DownloadThread(urlList, new DownloadFinishHandler(), cachedImages).start();
//                } else if (urlList.size() == 0) {
//                    displayText();
//                } else if (!CommonUtilities.isOnline() && urlList.size() != 0) {
//                    displayOnlyText();
//                }
//
//                try {
//                    descriptionLinearLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                } catch (NoSuchMethodError nsme) {
//                    descriptionLinearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                }
//            }
//        });
    }

    private void setScrollViewOffset() {

    }

    public void goToFullArticle(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(feed.getLink()));
        startActivity(intent);
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.details_menu, menu);
//
//        MenuItem menuItem = menu.findItem(R.id.action_favourite);
//
//        if (dao.isFavourite(feed.getGuid())) {
//            isArticleFavourite = true;
//            menuItem.setIcon(R.drawable.ic_action_rating_important);
//        } else menuItem.setIcon(R.drawable.ic_action_rating_not_important);
//    }

//    @Override
//    @TargetApi(14)
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        switch (id) {
//            case android.R.id.home: {
//                getActivity().onBackPressed();
//                return true;
//            }
//
//            case R.id.action_favourite: {
//
//                if (isArticleFavourite) {
//                    boolean isRemoved = dao.removeFromFavourites(feed.getGuid());
//                    if (isRemoved) {
//                        item.setIcon(R.drawable.ic_action_rating_not_important);
//                        CommonUtilities.showToast(getResources().getString(R.string.article_removed_from_fav));
//                    }
//                } else {
//                    boolean isAdded = dao.addToFavourites(feed.getGuid());
//                    if (isAdded) {
//                        item.setIcon(R.drawable.ic_action_rating_important);
//                        CommonUtilities.showToast(getResources().getString(R.string.article_added_to_fav));
//                    }
//                }
//
//                isArticleFavourite = !isArticleFavourite;
//
//                return true;
//            }
//
//            case R.id.action_share: {
//
//                Intent sendIntent = new Intent(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this article " + feed.getLink());
//                sendIntent.setType("text/plain");
//
//                startActivity(sendIntent);
//                return true;
//            }
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    private void getImagesUrl() {
        urlList = new ArrayList<>();
        Html.fromHtml(feed.getDescription(), new Html.ImageGetter() {

            @Override
            public Drawable getDrawable(String source) {
                urlList.add(source);
                return null;
            }
        }, null);
    }

    private void displayOnlyText() {

        titleView.setText(feed.getTitle());
        creatorView.setText(feed.getCreator());
        dateView.setText(CommonUtilities.getFormattedDate(feed.getDate()));
        descriptionView.setText(Html.fromHtml(feed.getDescription(), null, null));
    }

    private void displayText() {

        displayOnlyText();

        descriptionView.setText(Html.fromHtml(feed.getDescription(), new Html.ImageGetter() {

            Drawable drawable;
            int w;
            int h;
            double ratio;

            @Override
            public Drawable getDrawable(String source) {

                if (CommonUtilities.getCachedImage(source) != null) {

                    drawable = CommonUtilities.getCachedImage(source);
                    w = drawable.getIntrinsicWidth();
                    h = drawable.getIntrinsicHeight();
                    ratio = (double) w / h;

                    drawable.setBounds(0, 0, descriptionLayoutWidth, (int) (descriptionLayoutWidth / ratio));
                }

                return drawable;
            }
        }, null));

        /*Bitmap bitmap = CommonUtilities.getCachedBlurredImage(urlList.get(0).toString());

        if(bitmap != null) {
            backgroundImageView.setImageBitmap(bitmap);
        }*/
    }

//    private class DownloadThread extends Thread {
//
//        private Handler handler;
//        private List<URL> urlList;
//        private HashMap<String, BitmapDrawable> cache;
//        private Drawable image;
//
//        public DownloadThread(List<URL> urlList, Handler handlerIn, HashMap<String, BitmapDrawable> cacheIn) {
//
//            handler = handlerIn;
//            this.urlList = urlList;
//            cache = cacheIn;
//        }
//
//        @Override
//        public void run() {
//            super.run();
//
//            try {
//                for (URL url : urlList) {
//                    image = BitmapDrawable.createFromStream(url.openStream(), url.toString());
//                    cache.put(url.toString(), (BitmapDrawable) image);
//                }
//
//                handler.sendEmptyMessage(IMAGE_DOWNLOAD_QUEUE_COMPLETED);
//            } catch (Exception e) {
//                e.printStackTrace();
//                handler.sendEmptyMessage(IMAGE_DOWNLOAD_CONNECTION_EXCEPTION);
//            }
//        }
//    }

//    private class DownloadFinishHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            if (msg.what == IMAGE_DOWNLOAD_QUEUE_COMPLETED) {
//
//                getActivity().setProgressBarIndeterminateVisibility(false);
//
//                CommonUtilities.setCachedImages(cachedImages);
//
//                titleView.setText(feed.getTitle());
//                creatorView.setText(feed.getCreator());
//                dateView.setText(CommonUtilities.getFormattedDate(feed.getDate()));
//
//                URL url = urlList.get(0);
//                BitmapDrawable d = cachedImages.get(url.toString());
//                //Bitmap blurredBitmap = blurImage(d.getBitmap());
//                //CommonUtilities.addCachedBlurredImage(url.toString(), blurredBitmap);
//
//                displayText();
//
//                descriptionView.setMovementMethod(LinkMovementMethod.getInstance());
//            } else if (msg.what == IMAGE_DOWNLOAD_CONNECTION_EXCEPTION) {
//                displayOnlyText();
//            }
//        }
//    }
}
