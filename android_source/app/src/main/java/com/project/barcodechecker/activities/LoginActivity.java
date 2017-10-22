package com.project.barcodechecker.activities;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle(getString(R.string.button_login));
        btnLogin = (Button) findViewById(R.id.btn_login_lgn_atv);
        txtRegister = (TextView) findViewById(R.id.txt_register_atv);
        txtErrPwd = (TextView) findViewById(R.id.txt_error_password);
        txtErrUsr = (TextView) findViewById(R.id.txt_error_username);
        edtUsername = (EditText) findViewById(R.id.edt_username_login_atv);
        edtPassword = (EditText) findViewById(R.id.edt_password_login_atv);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProcess();
            }
        });
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void loginProcess() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String userError = checkValid(username, "Username");
        String passwordError = checkValid(password, "Password");
        if (userError.length() != 0) {
            txtErrUsr.setText(userError);
            txtErrUsr.setVisibility(View.VISIBLE);
            return;
        } else {

            txtErrUsr.setVisibility(View.INVISIBLE);
        }

        if (passwordError.length() != 0) {
            txtErrPwd.setVisibility(View.VISIBLE);
            txtErrPwd.setText(passwordError);
            return;
        } else {
            txtErrPwd.setVisibility(View.INVISIBLE);

        }
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
