package com.adrianlesniak.gamerspot.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.interfaces.OnHeadlineSelectedListener;
import com.adrianlesniak.gamerspot.models.NewsFeed;
import com.adrianlesniak.gamerspot.utilities.CommonUtilities;

/**
 * Created by Adrian on 10-Jun-14.
 */
public class FeedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context mContext;
    private OnHeadlineSelectedListener mCallback;

    private TextView date_textView;
    private TextView title_textView;
    private TextView creator_textView;
    private View image_holder;
    private RelativeLayout infoView;
    private NewsFeed mFeed;

    public FeedViewHolder(Context c, View itemView, OnHeadlineSelectedListener callback) {
        super(itemView);

        mContext = c;
        mCallback = callback;
        itemView.setOnClickListener(this);

        title_textView = (TextView) itemView.findViewById(R.id.feed_title);
        date_textView = (TextView) itemView.findViewById(R.id.feed_date);
        creator_textView = (TextView) itemView.findViewById(R.id.feed_creator);
        image_holder = itemView.findViewById(R.id.image_holder);
        infoView = (android.widget.RelativeLayout) itemView.findViewById(R.id.info_view);
    }

    public void setItem(NewsFeed feed) {

        mFeed = feed;

        title_textView.setText(mFeed.getTitle());
        creator_textView.setText(mFeed.getProvider());
//        image_holder.setBackgroundColor(mContext.getResources().getColor(getColor(mFeed.getPlatform())));

        //TODO add data back to list item
        //date_textView.setText(CommonUtilities.getFormattedDate(mFeed.getDate()));
    }

    private int getColor(int platform) {

        int color = 0;

        switch (platform) {
            case 1: {
                color = R.color.platform_pc;
                break;
            }

            case 2: {
                color = R.color.platform_xbox;
                break;
            }

            case 3: {
                color = R.color.platform_ps;
                break;
            }

            case 4: {
                color = R.color.platform_nintendo;
                break;
            }

            case 5: {
                color = R.color.platform_mobile;
                break;
            }
        }

        return color;
    }

    @Override
    public void onClick(View v) {
        mCallback.onArticleSelected(mFeed, false);
    }
}
