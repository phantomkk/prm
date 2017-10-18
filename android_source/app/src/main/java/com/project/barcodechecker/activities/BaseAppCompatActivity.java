package com.project.barcodechecker.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Lenovo on 18/10/2017.
 */

public abstract class BaseAppCompatActivity extends AppCompatActivity {
    ImageButton mBack;
    TextView mTitle;
    CircleImageView mAvatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(getLayoutResourceId());

    }

    protected abstract int getLayoutResourceId();

}
