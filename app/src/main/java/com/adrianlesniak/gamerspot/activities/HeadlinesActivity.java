package com.adrianlesniak.gamerspot.activities;

import android.content.Intent;
import android.os.Bundle;

import com.adrianlesniak.gamerspot.BuildConfig;
import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.fragments.NewsHeadlinesFragment;
import com.adrianlesniak.gamerspot.interfaces.OnHeadlineSelectedListener;
import com.adrianlesniak.gamerspot.models.NewsFeed;
import com.crashlytics.android.Crashlytics;


public class HeadlinesActivity extends NavigationDrawerActivity implements OnHeadlineSelectedListener {

    private NewsHeadlinesFragment headlinesFragment;
    private String[] mQueryValues;
    private Bundle mFragmentBundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG != true) {
            Crashlytics.start(this);
        }

        mQueryValues = getResources().getStringArray(R.array.drawer_news_items_query_keys);
        mFragmentBundle = new Bundle();

        headlinesFragment = new NewsHeadlinesFragment();
        displayHeadlines(savedInstanceState);
    }

    @Override
    protected void downloadFeedsForPosition(int positionIn) {

        mFragmentBundle.clear();
        mFragmentBundle.putString("serviceQueryValue", mQueryValues[positionIn]);


        //TODO Instead of creating new fragment, send message to the headlinesFragment to update RecyclerViewAdapter
        headlinesFragment = new NewsHeadlinesFragment();
        headlinesFragment.setArguments(mFragmentBundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, headlinesFragment, "").commit();
    }

    private void displayHeadlines(Bundle savedInstanceState) {

        if (findViewById(R.id.content_frame) != null) {

            if (savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, headlinesFragment, "").commit();
        }
    }

    public void onArticleSelected(NewsFeed feedClicked, boolean isSearched) {

        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra("FEED", feedClicked);

        startActivity(intent);
        //overridePendingTransition(R.anim.activity_slide_to_top, R.anim.activity_stay_visible);
    }
}
