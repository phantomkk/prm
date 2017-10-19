package com.project.barcodechecker.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.CoreManager;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by lucky on 13-Sep-17.
 */

public abstract class BaseActivity extends Activity {
    public Toolbar toolbar;
    private TextView txtTitle;
    private ImageButton btnBack;
    private ProgressDialog progressDialog;
    private CircleImageView imgAvatar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(getLayoutResourceId());
        initToolbar();
        txtTitle = (TextView) findViewById(R.id.txt_toolbar_title);
        imgAvatar = (CircleImageView) findViewById(R.id.profile_image);
        imgAvatar.setOnClickListener(imgClickListener);
        User u = CoreManager.getUser(this);
        if (u != null && u.getAvatar() != null) {
            Picasso.with(this).load(u.getAvatar()).error(R.drawable.ic_shopping_cart_black_24dp).into(imgAvatar);
        }
    }

    private View.OnClickListener imgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //todoư
        }
    };

    protected abstract int getLayoutResourceId();

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void initToolbar() {
        Log.e("TOOLBAR", "INIT");
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

    public void hideButtonBack(boolean hide) {
        if (btnBack != null) {
            if (hide) {
                btnBack.setVisibility(View.INVISIBLE);
            } else {
                btnBack.setVisibility(View.VISIBLE);
            }
        } else {
            Log.e("ERROR", "BaseActivity: btnBack is null");
        }
    }

    public void setToolbarTitle(String title) {
        if (toolbar != null) {
            txtTitle.setText(title);
        } else {
            Log.e("ERROR", "BaseActivity.setToolbarTitle(*): toolbar null");
        }
    }


    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    showMessage("Cancel dialog");
                }
            });
            progressDialog.show();
        }
    }
    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public void logError(String activity, String method, String message) {
        Log.e("LOG_ERROR", activity + "." + method + "(): " + message);
    }
}
