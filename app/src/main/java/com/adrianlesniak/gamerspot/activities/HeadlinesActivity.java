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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG != true) {
            Crashlytics.start(this);
        }

        headlinesFragment = new NewsHeadlinesFragment();
        displayHeadlines(savedInstanceState);
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
