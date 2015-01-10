package com.adrianlesniak.gamerspot.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.adrianlesniak.gamerspot.R;

/**
 * Created by Adrian on 05-Jan-15.
 */
public class ToolbarActivity extends ActionBarActivity {

    protected Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        obtainReferences();
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().getThemedContext();
    }

    private void obtainReferences() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }
}
