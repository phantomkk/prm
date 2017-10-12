package com.project.barcodechecker.activities;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.project.barcodechecker.R;
import com.squareup.picasso.Picasso;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Picasso.with(this).load("https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt-small.jpg").into((ImageView)findViewById(R.id.img_test));
    }
}
