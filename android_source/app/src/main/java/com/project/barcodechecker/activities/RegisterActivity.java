package com.project.barcodechecker.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.utils.Utils;

public class RegisterActivity extends BaseActivity {
    private Button btnRegister;
    private ProgressBar mPbPassword, mPbConfirmPassword;
    private EditText edtUsername, edtName, edtAddress,edtEmail,edtPhone, edtPassword, edtRepassword;
    private TextView txtLinkLogin;
    private TextInputLayout userWrapper, nameWrapper, addressWrapper, emailWrapper, phoneWrapper, passwordWrapper, confirmWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Đăng kí tài khoản");
        hideAvatar(true);
        btnRegister = (Button) findViewById(R.id.btn_register_reg_atv);
        edtUsername = (EditText) findViewById(R.id.edt_username);
        edtName = (EditText) findViewById(R.id.edt_name);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtRepassword = (EditText) findViewById(R.id.edt_confirm_password);
        txtLinkLogin = (TextView) findViewById(R.id.txt_register_atv);
        userWrapper = (TextInputLayout) findViewById(R.id.userWrapper);
        nameWrapper = (TextInputLayout) findViewById(R.id.nameWrapper);
        addressWrapper = (TextInputLayout) findViewById(R.id.addressWrapper);
        emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
        phoneWrapper = (TextInputLayout) findViewById(R.id.phoneWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        confirmWrapper = (TextInputLayout) findViewById(R.id.confirmWrapper);
        userWrapper.setHint(getString(R.string.hint_username));
        nameWrapper.setHint(getString(R.string.hint_name));
        addressWrapper.setHint(getString(R.string.hint_address));
        emailWrapper.setHint(getString(R.string.hint_email));
        phoneWrapper.setHint(getString(R.string.hint_phone));
        passwordWrapper.setHint(getString(R.string.hint_password));
        confirmWrapper.setHint(getString(R.string.hint_password_retype));
        mPbPassword = (ProgressBar) findViewById(R.id.progressBar_password);
        mPbConfirmPassword = (ProgressBar) findViewById(R.id.progressBar_confirm_password);


        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.checkPassword(getApplicationContext(), edtPassword, mPbPassword);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edtRepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Utils.checkPassword(getApplicationContext(), edtRepassword, mPbConfirmPassword);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){

                }
            }
        });
        txtLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean isValid(){
        boolean flag = true;
        String username =edtUsername.getText().toString().trim();
        String name =edtName.getText().toString().trim();
        String address =edtAddress.getText().toString().trim();
        String email =edtEmail.getText().toString().trim();
        String phone =edtPhone.getText().toString().trim();
        String password =edtPassword.getText().toString().trim();
        String confirmPassword =edtRepassword.getText().toString().trim();
        if(username.length()<8 || username.length()>25 ){
            flag=false;
            edtUsername.setText("");
            userWrapper.setError("Độ dài tài khoản không hợp lệ.");
        } else{
            flag=true;
            userWrapper.setError("");
            userWrapper.setErrorEnabled(false);
        }
        if(name.length()<3 || name.length()>25 ){
            flag=false;
            edtName.setText("");
            nameWrapper.setError("Độ dài tên không hợp lệ.");
        } else{
            flag=true;
            nameWrapper.setError("");
            nameWrapper.setErrorEnabled(false);
        }

        if(address.length()<8 || address.length()>25 ){
            flag=false;
            edtAddress.setText("");
            addressWrapper.setError("Độ dài địa chỉ không hợp lệ.");
        } else{
            flag=true;
            addressWrapper.setError("");
            addressWrapper.setErrorEnabled(false);
        }


        if(email.length()<8 || email.length()>25 ){
            flag=false;
            edtEmail.setText("");
            emailWrapper.setError("Độ dài email không hợp lệ.");
        }else if(!Utils.checkEmail(email)) {
            flag=false;
            edtEmail.setText("");
            emailWrapper.setError("Email không hợp lệ");
        }else{
            flag=true;
            emailWrapper.setError("");
            emailWrapper.setErrorEnabled(false);
        }

        if(phone.length()<8 || phone.length()>25 ){
            flag=false;
            edtPhone.setText("");
            phoneWrapper.setError("Độ dài số điện thoại không hợp lệ.");
        }else if(!Utils.checkPhone(phone)) {
            flag=false;
            edtPhone.setText("");
            phoneWrapper.setError("Số điện thoại không hợp lệ");
        }else{
            flag=true;
            phoneWrapper.setError("");
            phoneWrapper.setErrorEnabled(false);
        }

        if (password.length() < 1) {
            flag = false;
            passwordWrapper.setError(getString(R.string.password_not_empty_message));
        } else if (password.length() < 8) {
            flag = false;
            passwordWrapper.setError(getString(R.string.password_leng_error));
        } else {
            flag = true;
            passwordWrapper.setError("");
            passwordWrapper.setErrorEnabled(false);
        }

        if (!confirmPassword.equals(password)) {
            flag = false;
            confirmWrapper.setError(getString(R.string.password_not_match));
        } else if (confirmPassword.length() < 1) {
            flag = false;
            confirmWrapper.setError(getString(R.string.password_not_empty_message));
        } else if (confirmPassword.length() < 8) {
            flag = false;
            confirmWrapper.setError(getString(R.string.password_leng_error));
        } else {
            flag = true;
            confirmWrapper.setVisibility(View.GONE);
        }



        return flag;
    }



    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }
}
