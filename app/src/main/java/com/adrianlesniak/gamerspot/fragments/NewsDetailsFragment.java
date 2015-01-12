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
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.database.DAO;
import com.adrianlesniak.gamerspot.interfaces.FullArticleClickListener;
import com.adrianlesniak.gamerspot.utilities.CommonUtilities;
import com.adrianlesniak.gamerspot.utilities.NewsFeed;
import com.adrianlesniak.gamerspot.views.FeedImage;
import com.adrianlesniak.gamerspot.views.StickyScrollView;
import com.melnykov.fab.FloatingActionButton;

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
    @InjectView(R.id.layout_scroll_view)
    StickyScrollView mScrollView;
    @InjectView(R.id.scroll_view_space)
    Space mSpace;
    @InjectView(R.id.fab)
    FloatingActionButton mFab;
    @InjectView(R.id.details_header_layout)
    RelativeLayout mHeaderView;
    //    @InjectView(R.id.button_full_article) GamerSpotButton fullArticleButton;

    private String LOG = "ATTACH";
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
        View view =  inflater.inflate(R.layout.fragment_news_details, container, false);

        ButterKnife.inject(this, view);

        if (urlList.size() >= 1) {
            mFeedMainImage.setVisibility(View.VISIBLE);
            mSpace.setVisibility(View.VISIBLE);
            mFeedMainImage.setImageFromUrl(urlList.get(0));
        }

        mScrollView.attachViewForParallax(mFeedMainImage);
        mFab.attachToScrollView(mScrollView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFeedMainImage.setSpaceForScrollView(mSpace);

        titleView.setText(feed.getTitle());
        titleView.setTextColor(getResources().getColor(android.R.color.white));
        creatorView.setText(feed.getCreator());
        dateView.setText(CommonUtilities.getFormattedDate(feed.getDate()));
        descriptionView.setText(Html.fromHtml(feed.getDescription().replaceAll("<img.+?>", "")));
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
}
