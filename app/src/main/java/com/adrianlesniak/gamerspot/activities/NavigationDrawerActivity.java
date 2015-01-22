package com.adrianlesniak.gamerspot.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;

import com.adrianlesniak.gamerspot.R;
import com.adrianlesniak.gamerspot.fragments.NavigationDrawerFragment;

/**
 * Created by Adrian on 05-Jan-15.
 */
public abstract class NavigationDrawerActivity extends ToolbarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        obtainReferences();
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    private void obtainReferences() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        downloadFeedsForPosition(position);
    }

    protected abstract void downloadFeedsForPosition(int positionIn);

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
