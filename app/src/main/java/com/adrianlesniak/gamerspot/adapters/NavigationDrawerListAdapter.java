package com.adrianlesniak.gamerspot.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.views.DrawerListViewHolder;

/**
 * Created by Adrian Lesniak on 16-Jun-14.
 */

public class NavigationDrawerListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] newsItems;
    private LayoutInflater mInflater;
    private DrawerListViewHolder holder;

    //TODO Adapter busted
    public NavigationDrawerListAdapter(Context context) {
        super(context, 0);

        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        newsItems = context.getResources().getStringArray(R.array.drawer_news_items);
    }

    @Override
    public int getCount() {
        return newsItems.length;
    }

    @Override
    public String getItem(int position) {
        return newsItems[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.navigation_drawer_news_item, null);

            holder = new DrawerListViewHolder();
            holder.newsItem = (android.widget.TextView) convertView.findViewById(R.id.drawer_news_listItem_textView);
            holder.indicator = convertView.findViewById(R.id.drawer_news_listItem_indicator);
            holder.itemIcon = (android.widget.ImageView) convertView.findViewById(R.id.item_icon);

            convertView.setTag(holder);

        } else {
            holder = (DrawerListViewHolder) convertView.getTag();
        }

        holder.newsItem.setText(getItem(position));

        int color = 0;
        int icon = 0;

        switch (position) {

            case 0: {
                color = R.color.drawer_item_all;
                icon = R.drawable.drawer_icon_all;
                break;
            }
            case 1: {
                color = R.color.drawer_item_pc;
                icon = R.drawable.drawer_icon_pc;
                break;
            }
            case 2: {
                color = R.color.drawer_item_xbox;
                icon = R.drawable.drawer_icon_xbox;
                break;
            }
            case 3: {
                color = R.color.drawer_item_ps;
                icon = R.drawable.drawer_icon_ps;
                break;
            }
            case 4: {
                color = R.color.drawer_item_nintendo;
                icon = R.drawable.drawer_icon_nintendo;
                break;
            }
            case 5: {
                color = R.color.drawer_item_mobile;
                icon = R.drawable.drawer_icon_mobile;
                break;
            }
            case 6: {
                color = R.color.drawer_item_favourite;
                icon = R.drawable.drawer_icon_favourites;
                break;
            }
        }

        if (icon != 0 && color != 0) {
            holder.indicator.setBackgroundColor(mContext.getResources().getColor(color));
            holder.itemIcon.setImageDrawable(mContext.getResources().getDrawable(icon));
            setAlphaOnIcon(holder.itemIcon);
        }

        return convertView;
    }

    private void setAlphaOnIcon(View iconIn) {

        if (Build.VERSION.SDK_INT < 11) {
            final AlphaAnimation animation = new AlphaAnimation(0.5f, 0.5f);
            animation.setDuration(0);
            animation.setFillAfter(true);
            iconIn.startAnimation(animation);
        } else {
            iconIn.setAlpha(0.5f);
        }

    }
}
