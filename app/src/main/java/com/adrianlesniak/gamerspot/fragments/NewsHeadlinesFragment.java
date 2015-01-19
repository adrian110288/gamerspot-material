package com.adrianlesniak.gamerspot.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.adapters.NewsFeedsRecyclerViewAdapter;
import com.adrianlesniak.gamerspot.interfaces.OnHeadlineSelectedListener;
import com.adrianlesniak.gamerspot.models.NewsFeed;
import com.adrianlesniak.gamerspot.utilities.FeedsLoader;
import com.adrianlesniak.gamerspot.utilities.Utils;
import com.adrianlesniak.gamerspot.views.CustomTypefaceSpan;
import com.adrianlesniak.gamerspot.views.GamerspotRecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Adrian on 13-Jun-14.
 */
public class NewsHeadlinesFragment extends Fragment implements LoaderManager.LoaderCallbacks/*implements AbsListView.OnScrollListener*/ {

    @InjectView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @InjectView(R.id.headlines_recycler_view)
    GamerspotRecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<NewsFeed> feedList;
    private NewsFeedsRecyclerViewAdapter feedsAdapter;
    private OnHeadlineSelectedListener mCallback;
//    private SearchDialogFragment searchDialogFragment;
//    private AboutDialogFragment aboutDialogFragment;
//    private int mLastFirstVisibleItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_headlines, null);
        ButterKnife.inject(this, v);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnHeadlineSelectedListener) {
            mCallback = (OnHeadlineSelectedListener) activity;
        }
    }

//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//        if (scrollState == SCROLL_STATE_IDLE) {
//            if (listView.getLastVisiblePosition() >= listView.getCount() - 1) {
//                loadMoreData();
//            }
//        }
//    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSwipeRefreshLayout.setEnabled(false);

        if (Utils.isOnline(mContext)) {
            getLoaderManager().initLoader(0, null, this).forceLoad();
        } else {
            mSwipeRefreshLayout.setEnabled(true);
            Utils.showToast(mContext, getResources().getString(R.string.no_network_connection));
        }
//        registerForContextMenu(listView);

    }

//    private void loadMoreData() {
//
//        long platformId = NavigationDrawerFragment.getDrawerItemSelected();
//        ArrayList<NewsFeed> dataToAttach;
//
//        if (platformId == 0) {
//            dataToAttach = dao.loadMoreDataForScroll(null);
//        } else {
//            dataToAttach = dao.loadMoreDataForScroll(platformId);
//        }
//
//        feedList.addAll(dataToAttach);
//        feedsAdapter.notifyDataSetChanged();
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        SpannableString spannableString;

        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            spannableString = new SpannableString(menuItem.getTitle());
            spannableString.setSpan(new CustomTypefaceSpan(getActivity(), "fonts/GeosansLight.ttf"), 0, menuItem.getTitle().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            menuItem.setTitle(spannableString);
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new FeedsLoader(mContext);
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        feedList = (ArrayList<NewsFeed>) data;
        feedsAdapter = new NewsFeedsRecyclerViewAdapter(mContext, feedList, mCallback);
        mRecyclerView.setAdapter(feedsAdapter);
        mSwipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mRecyclerView.setAdapter(null);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//
//        if (id == R.id.action_search) {
//
//            searchDialogFragment = new SearchDialogFragment();
//            searchDialogFragment.setTargetFragment(this, 1);
//            searchDialogFragment.show(getFragmentManager(), "search");
//
//            return true;
//        }
//
//        if (id == R.id.action_about) {
//            aboutDialogFragment = new AboutDialogFragment();
//            aboutDialogFragment.setTargetFragment(this, 1);
//            aboutDialogFragment.show(getFragmentManager(), "about");
//        }
//
//        return false;
//    }

//    public void refresh(long id) {
//
//        if (id == 6) {
//            feedList = dao.getAllFavourites();
//
//            if (feedList.size() == 0) {
//                CommonUtilities.showToast("No favourites");
//            }
//        } else {
//
//            if (id > 0 && id < 6) {
//                feedList = dao.getFeeds(id);
//            } else if (id == 0) {
//                feedList = dao.getFeeds(null);
//            }
//
//        }
//
//        feedsAdapter = new NewsFeedsRecyclerViewAdapter(mContext, feedList, mCallback);
//        mRecyclerView.setAdapter(feedsAdapter);
//        mRecyclerView.smoothScrollToPosition(0);
//    }

//    private class FeedFetchHandler extends Handler {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            int newFeeds = msg.what;
//
//            if (newFeeds > 0 && NavigationDrawerFragment.getDrawerItemSelected() == 0) {
//                refresh(0);
//            }
////            CommonUtilities.showToast(getResources().getQuantityString(R.plurals.new_feeds_plurals, newFeeds, newFeeds));
//        }
//    }
}
