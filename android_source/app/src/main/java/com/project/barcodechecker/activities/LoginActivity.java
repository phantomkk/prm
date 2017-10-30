package com.project.barcodechecker.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.CoreManager;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    private Button btnLogin;
    private TextView txtRegister, txtErrUsr, txtErrPwd;
    private EditText edtUsername, edtPassword;
    private TextInputLayout usernameWrapper, passwordWrapper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.button_login));
        hideAvatar(true);
        btnLogin = (Button) findViewById(R.id.btn_login_lgn_atv);
        txtRegister = (TextView) findViewById(R.id.txt_register_atv);
        edtUsername = (EditText) findViewById(R.id.edt_username_login_atv);
        edtPassword = (EditText) findViewById(R.id.edt_password_login_atv);
        btnLogin.setVisibility(View.VISIBLE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    loginProcess();
                }
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        usernameWrapper = (TextInputLayout) findViewById(R.id.userWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        usernameWrapper.setHint("Tài khoản");
        passwordWrapper.setHint("Mật Khẩu");

    }

    public void loginProcess() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
//        String userError = checkValid(username, "Username");
//        String passwordError = checkValid(password, "Password");
//        if (userError.length() != 0) {
//            txtErrUsr.setText(userError);
//            txtErrUsr.setVisibility(View.VISIBLE);
//            return;
//        } else {
//
//            txtErrUsr.setVisibility(View.INVISIBLE);
//        }
//
//        if (passwordError.length() != 0) {
//            txtErrPwd.setVisibility(View.VISIBLE);
//            txtErrPwd.setText(passwordError);
//            return;
//        } else {
//            txtErrPwd.setVisibility(View.INVISIBLE);
//
//        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        UserService userService = APIServiceManager.getUserService();
        showLoading();
        userService.login(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    CoreManager.setUser(LoginActivity.this, response.body());
                    setResult(RESULT_OK);
                    finish();
                } else {
                    logError(LoginActivity.class.getSimpleName(), "loginProcess","API ELSE");
                }
                hideLoading();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                logError(LoginActivity.class.getSimpleName(), "loginProcess", "Failure " + t.getMessage());
                hideLoading();
            }
        });

    }

    public boolean isValid(){
        boolean flag = true;
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if(username.length()<8 || username.length()>25 ){
            flag=false;
            edtUsername.setText("");
            usernameWrapper.setError("Độ dài tài khoản không hợp lệ.");
        } else{
            flag=true;
            usernameWrapper.setError("");
            usernameWrapper.setErrorEnabled(false);
        }

        if(password.length()<8 || password.length()>25 ){
            flag=false;
            edtPassword.setText("");
            passwordWrapper.setError("Độ dài mật khẩu không hợp lệ.");
        }else{
            flag=true;
            usernameWrapper.setError("");
            passwordWrapper.setErrorEnabled(false);
        }
        return flag;
    }

    public String checkValid(String value, String type) {

        if (value == null || value.length() < 8 || value.length() > 25) {
            return "Độ dài " + type + " không hợp lệ. Vui lòng nhập lại.";
        }
        if (value.matches("[a-zA-Z0-9\\-\\_\\@]")) {
            return type + " chỉ chứa chữ, số và các kí tự -, _, @";
        }
        return "";
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_login;
    }
}
