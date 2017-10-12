package com.project.barcodechecker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.barcodechecker.R;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.button_login));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }
}
