package com.adrianlesniak.gamerspot.activities;

import android.os.Bundle;
import android.view.View;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.fragments.NewsDetailsFragment;
import com.adrianlesniak.gamerspot.models.NewsFeed;

/**
 * Created by Adrian Lesniak on 16-Jun-14.
 */
public class NewsDetailsActivity extends ToolbarActivity {

    private NewsDetailsFragment detailsFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewsFeed feedIn = (NewsFeed) getIntent().getSerializableExtra("FEED");
        boolean isSearched = getIntent().getBooleanExtra("isSearched", false);

        detailsFragment = new NewsDetailsFragment();
//        fullArticleClickListener = detailsFragment;
        Bundle b = new Bundle();
        b.putSerializable("FEED", feedIn);
        b.putBoolean("searched", isSearched);
        detailsFragment.setArguments(b);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, detailsFragment).commit();
    }

    public void goToFullArticle(View view) {
        detailsFragment.goToFullArticle(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.activity_stay_visible, R.anim.activity_slide_to_bottom);
    }
}
