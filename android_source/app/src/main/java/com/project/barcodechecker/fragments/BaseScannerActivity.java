package com.project.barcodechecker.fragments;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.BaseActivity;

/**
 * Created by Lenovo on 13/10/2017.
 */

public class BaseScannerActivity extends AppCompatActivity {

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //final ActionBar ab = getSupportActionBar();
//        if(ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
