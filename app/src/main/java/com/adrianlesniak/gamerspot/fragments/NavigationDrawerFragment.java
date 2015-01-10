package com.adrianlesniak.gamerspot.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.adapters.NavigationDrawerListAdapter;

public class NavigationDrawerFragment extends Fragment {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private static int mCurrentSelectedPosition = 0;
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private boolean mDrawerFirstTimeOpened = true;

    private NavigationDrawerListAdapter navigationDrawerListAdapter;

    public NavigationDrawerFragment() {
    }

    public static int getDrawerItemSelected() {
        return mCurrentSelectedPosition;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mDrawerListView = (ListView) root.findViewById(R.id.drawer_listview);
        mDrawerListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationDrawerListAdapter = new NavigationDrawerListAdapter(getActivity());
        mDrawerListView.setAdapter(navigationDrawerListAdapter);

    }

    public void setUp(int fragmentId, final DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = drawerLayout;
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);

                if (mDrawerFirstTimeOpened) {
                    checkItem();
                    mDrawerFirstTimeOpened = false;
                }
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {

        if (mDrawerListView != null) {

            if (position != mCurrentSelectedPosition) {
                resetDrawerItem();
                mCurrentSelectedPosition = position;
                checkItem();
            }
        }

        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    private void resetDrawerItem() {

        new Runnable() {

            @Override
            public void run() {

                View drawerItem = mDrawerListView.getChildAt(mCurrentSelectedPosition);
                drawerItem.setBackgroundResource(R.drawable.navigation_drawer_item_selector);
                ((TextView) drawerItem.findViewById(R.id.drawer_news_listItem_textView)).setTextColor(getResources().getColorStateList((R.color.navigation_drawer_item_text_selector)));
//                drawerItem.findViewById(R.id.item_icon).setAlpha(0.5f);
                setAlphaOnIcon(drawerItem.findViewById(R.id.item_icon), 0.5f);
                drawerItem.findViewById(R.id.drawer_news_listItem_indicator).setVisibility(View.INVISIBLE);
            }
        }.run();
    }

    private void checkItem() {

        new Runnable() {

            @Override
            public void run() {
                View drawerItem = mDrawerListView.getChildAt(mCurrentSelectedPosition);
                drawerItem.setBackgroundResource(R.color.drawer_item_selected);
                ((TextView) drawerItem.findViewById(R.id.drawer_news_listItem_textView)).setTextColor(getResources().getColor((android.R.color.white)));
                setAlphaOnIcon(drawerItem.findViewById(R.id.item_icon), 1f);
                drawerItem.findViewById(R.id.drawer_news_listItem_indicator).setVisibility(View.VISIBLE);
            }
        }.run();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof NavigationDrawerCallbacks) {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setAlphaOnIcon(View iconIn, float alphaLevel) {

        float alphaFrom;
        float alphaTo;

        if (alphaLevel > 0.5) {
            alphaFrom = 0.5f;
            alphaTo = alphaLevel;
        } else {
            alphaFrom = alphaLevel;
            alphaTo = 0.5f;
        }


        if (Build.VERSION.SDK_INT < 11) {
            final AlphaAnimation animation = new AlphaAnimation(alphaFrom, alphaTo);
            animation.setDuration(0);
            animation.setFillAfter(true);
            iconIn.startAnimation(animation);
        } else {
            iconIn.setAlpha(alphaLevel);
        }

    }

    public static interface NavigationDrawerCallbacks {

        void onNavigationDrawerItemSelected(int position);
    }
}