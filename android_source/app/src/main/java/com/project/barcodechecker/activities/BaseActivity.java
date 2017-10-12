package com.project.barcodechecker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;

/**
 * Created by lucky on 13-Sep-17.
 */

public abstract class BaseActivity extends Activity {
    private Toolbar toolbar;
    private TextView txtTitle;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());
        initToolbar();
        txtTitle = (TextView) findViewById(R.id.txt_toolbar_title);
    }

    protected abstract int getLayoutResourceId();

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnBack = (ImageButton) findViewById(R.id.btn_back_toolbar);
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    public void setToolbarTitle(String title) {
        if (toolbar != null) {
            txtTitle.setText(title);
        } else {
            Log.e("ERROR", "BaseActivity.setToolbarTitle(*): toolbar null");
        }
    }
}
