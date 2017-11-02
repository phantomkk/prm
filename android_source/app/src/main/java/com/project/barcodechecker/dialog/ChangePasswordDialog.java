package com.project.barcodechecker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.LoginActivity;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.CoreManager;
import com.project.barcodechecker.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 29/10/2017.
 */

public class ChangePasswordDialog extends Dialog {
    private String mPassword;
    private EditText mCurrentPassword, mNewPassword, mConfirmPassword;
    private TextView mNewPasswordError, mCurrentPasswordError, mConfirmPasswordError;
    private ProgressBar mPbNewPassword, mPbCurrentPassword, mPbConfirmPassword;
    private Button button;
    private Context context;

    public ChangePasswordDialog(@NonNull Context context, String mPassword) {
        super(context);
        this.mPassword = mPassword;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.change_password_dialog);
        mCurrentPassword = (EditText) findViewById(R.id.currtent_password);
        mNewPassword = (EditText) findViewById(R.id.new_password);
        mConfirmPassword = (EditText) findViewById(R.id.confirm_password);
        mPbCurrentPassword = (ProgressBar) findViewById(R.id.progressBar_current);
        mPbNewPassword = (ProgressBar) findViewById(R.id.progressBar_new);
        mPbConfirmPassword = (ProgressBar) findViewById(R.id.progressBar_confirm);
        mCurrentPasswordError = (TextView) findViewById(R.id.current_password_error);
        mNewPasswordError = (TextView) findViewById(R.id.new_password_error);
        mConfirmPasswordError = (TextView) findViewById(R.id.confirm_password_error);
        mCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.checkPassword(getContext(),mCurrentPassword,mPbCurrentPassword);
//                callculator(mCurrentPassword, mPbCurrentPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.checkPassword(getContext(),mNewPassword,mPbNewPassword);
//                callculator(mNewPassword, mPbNewPassword);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.checkPassword(getContext(),mConfirmPassword,mPbConfirmPassword);
//                callculator(mConfirmPassword, mPbConfirmPassword);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button = (Button) findViewById(R.id.btn_change_password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid()) {
                    //todo changepassword
                    User user = CoreManager.getUser(context);
//                    user.setPassword(mNewPassword.getText().toString());
                    UserService userService = APIServiceManager.getUserService();
                    userService.updatePassword(user.getId(),mNewPassword.getText().toString()).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User userTemplate = CoreManager.getUser(context);
                                userTemplate.setPassword(mNewPassword.getText().toString());
                                CoreManager.setUser(context,userTemplate);
                                Toast.makeText(context, "Change Password success",
                                        Toast.LENGTH_LONG).show();
                                dismiss();
                            } else {
                                Toast.makeText(context, "Change Password error, please try again!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(context, "Change Password fail, please try again",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }


    public boolean isValid() {
        boolean flag = true;
        String current, newPass, confirmPass;
        current = mCurrentPassword.getText().toString().trim();
        newPass = mNewPassword.getText().toString().trim();
        confirmPass = mConfirmPassword.getText().toString().trim();
        if (!current.equals(mPassword)) {
            flag = false;
            mCurrentPassword.setText("");
            mCurrentPasswordError.setVisibility(View.VISIBLE);
            mCurrentPasswordError.setText(R.string.password_not_right);
        } else if (current.length() < 1) {
            flag = false;
            mCurrentPasswordError.setVisibility(View.VISIBLE);
            mCurrentPasswordError.setText(R.string.password_not_empty_message);
        } else if (newPass.length() < 8) {
            flag = false;
            mCurrentPasswordError.setVisibility(View.VISIBLE);
            mCurrentPasswordError.setText(R.string.password_leng_error);
        } else {
            mCurrentPasswordError.setVisibility(View.GONE);
        }


        if (newPass.length() < 1) {
            flag = false;
            mNewPasswordError.setVisibility(View.VISIBLE);
            mNewPasswordError.setText(R.string.password_not_empty_message);
        } else if (newPass.length() < 8) {
            flag = false;
            mNewPasswordError.setVisibility(View.VISIBLE);
            mNewPasswordError.setText(R.string.password_leng_error);
        } else {
            mNewPasswordError.setVisibility(View.GONE);
        }


        if (!confirmPass.equals(newPass)) {
            flag = false;
            mConfirmPasswordError.setVisibility(View.VISIBLE);
            mConfirmPasswordError.setText(R.string.password_not_match);
        } else if (confirmPass.length() < 1) {
            flag = false;
            mConfirmPasswordError.setVisibility(View.VISIBLE);
            mConfirmPasswordError.setText(R.string.password_not_empty_message);
        } else if (confirmPass.length() < 8) {
            flag = false;
            mConfirmPasswordError.setVisibility(View.VISIBLE);
            mConfirmPasswordError.setText(R.string.password_leng_error);
        } else {
            mConfirmPasswordError.setVisibility(View.GONE);
        }


        return flag;
    }

    public void callculator(EditText editText, ProgressBar progressBar) {
        String s = editText.getText().toString().trim();
        if (s.length() == 0)
            progressBar.setProgress(0);
        else if (s.length() < 3) {
            progressBar.setProgress(25);
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getColor(R.color.color_red_500)));
        } else if (s.length() < 8) {
            progressBar.setProgress(50);
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getColor(R.color.color_orange_500)));
        } else if (s.length() < 10) {
            progressBar.setProgress(75);
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getColor(R.color.color_yellow_500)));
        } else {
            progressBar.setProgress(100);
            progressBar.setProgressTintList(ColorStateList.valueOf(getContext().getColor(R.color.color_green_500)));
        }
    }


}
