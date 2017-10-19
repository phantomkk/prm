package com.project.barcodechecker.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.barcodechecker.R;

public class RegisterActivity extends BaseActivity {
    private Button btnRegister;
    private EditText edtUsername,edtEmail,edtPassword,edtRepassword;
    private TextView txtErrUsn, txtErrPwd, txtErrRepwd, txtErrEmail, txtLinkLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Đăng kí tài khoản");
        btnRegister = (Button) findViewById(R.id.btn_register_reg_atv);
        edtUsername = (EditText) findViewById(R.id.edt_username_reg_atv);
        edtEmail = (EditText) findViewById(R.id.edt_email_reg_atv);
        edtPassword = (EditText) findViewById(R.id.edt_password_reg_atv);
        edtRepassword = (EditText) findViewById(R.id.edt_re_pwd_reg_atv);
        txtErrUsn  = (TextView) findViewById(R.id.txt_error_username_reg_atv);
        txtErrEmail  = (TextView) findViewById(R.id.txt_error_email_reg_atv);
        txtErrPwd= (TextView) findViewById(R.id.txt_error_password_reg_atv);
        txtErrRepwd  = (TextView) findViewById(R.id.txt_error_repwd_reg_atv);
        txtLinkLogin  = (TextView) findViewById(R.id.txt_register_atv);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        txtLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_register;
    }
}
