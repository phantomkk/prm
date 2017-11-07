package com.project.barcodechecker.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.AppConst;
import com.project.barcodechecker.utils.CoreManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        User u = CoreManager.getUser(this);
        if (u != null && u.getAvatar() != null) {
            Picasso.with(this).load(u.getAvatar()).error(R.drawable.avatar).into(imgAvatar);
        }
//        if(u!=null){
        imgAvatar.setOnClickListener(imgClickListener);

//        }
    }

    private User user;

    public void searchUserAndTranferToUserDetail(int id) {
        UserService userService = APIServiceManager.getUserService();
        userService.getByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    user = response.body();
                    Intent intent = new Intent(BaseActivity.this, UserDetailActivity.class);
                    intent.putExtra(AppConst.USER_PARAM, user);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Loading fail",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Loading fail",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private View.OnClickListener imgClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (CoreManager.getUser(BaseActivity.this) != null) {
                searchUserAndTranferToUserDetail(CoreManager.getUser(BaseActivity.this).getId());
            }
        }
    };

    public void logErrorBody(Response response) throws IOException {
        if (response.errorBody() != null) {
            Log.e("LOG_ERROR", response.errorBody().string());
        } else {
            Log.e("LOG_ERROR", "error body is null");
        }
    }

    protected abstract int getLayoutResourceId();

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackground(ContextCompat.getDrawable(this, R.color.colorPrimary));
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

    public void hideAvatar(boolean hide) {
        if (imgAvatar != null) {
            if (hide) {
                imgAvatar.setVisibility(View.INVISIBLE);
            } else {
                imgAvatar.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setUpAvatar() {
//        Picasso.with(this).invalidate(CoreManager.getUser(this).getAvatar());
        Picasso.with(this).load(CoreManager.getUser(this).getAvatar()).into(imgAvatar);
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
            txtTitle.setTextColor(getColor(R.color.color_white));
        } else {
            Log.e("ERROR", "BaseActivity.setToolbaloadrTitle(*): toolbar null");
        }
    }


    public void showLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Đang tải...");
            progressDialog.setCancelable(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //notworking because .setCancelable above
                }
            });
            progressDialog.show();
        }
    }

    public void showLoading(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
//                     notworking because .setCancelable above
                }
            });
            progressDialog.show();
        }
    }

    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public void logError(String activity, String method, String message) {
        Log.e("LOG_ERROR", activity + "." + method + "(): " + message);
    }

    public void logError(String message) {
        Log.e("LOG_ERROR", message);
    }

    public void logError(Response response, String activity) throws IOException {
        if (response.isSuccessful()) {
            Log.e(AppConst.LOG_ERROR, activity + " Response success: " + activity + ": ");
        } else {
            if (response.errorBody() != null) {
                Log.e(AppConst.LOG_ERROR, activity + " Response error code " + response.code() + "Error message: " +
                        response.errorBody().string());
            } else {
                Log.e("LOG_ERROR", activity + " Response error body null");
            }
        }
    }
}
