package com.project.barcodechecker.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by lucky on 13-Sep-17.
 */

public abstract class BaseActivity extends Activity {
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());
    }

    protected abstract int getLayoutResourceId();
}
