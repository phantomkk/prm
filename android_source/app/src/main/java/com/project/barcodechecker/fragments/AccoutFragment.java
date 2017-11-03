package com.project.barcodechecker.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.MainActivity;
import com.project.barcodechecker.activities.TestActivity;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.FileService;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.dialog.ChangePasswordDialog;
import com.project.barcodechecker.models.ImgResponse;
import com.project.barcodechecker.models.User;
import com.project.barcodechecker.utils.CoreManager;
import com.project.barcodechecker.utils.Utils;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lenovo on 21/10/2017.
 */

public class AccoutFragment extends LoadingFragment implements View.OnClickListener{
    public AccoutFragment() {
        // Required empty public constructor
    }

    private ImageButton btnChangeAvarta, btnChangePassWord;
    private TextView tvName;
    private EditText edtName, edtAddress, edtEmail, edtPhone, edtIntroduct, edtWeb;
    private TextView tvNameError, tvAddressError, tvEmailError, tvPhoneError;
    private Button btnConfirm, btnLogOut;
    private CircleImageView imvAvatar;
    public static final int REQUEST_PERMISSION = 102;
    private OnLoginListener mCallback;
    private User u;
    private TextInputLayout nameWrapper, addressWrapper, emailWrapper, phoneWrapper;
    public interface OnLoginListener {
        public void destoyFragmentUser();
        public void changeAvatar(Uri uri);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_accout, container, false);
        u = CoreManager.getUser(getContext());
        imvAvatar = (CircleImageView) view.findViewById(R.id.img_avatar_user);
        btnChangeAvarta = (ImageButton) view.findViewById(R.id.btn_change_avatar);
        btnChangeAvarta.setOnClickListener(this);
        btnChangePassWord = (ImageButton) view.findViewById(R.id.btn_edit_password);
        btnChangePassWord.setOnClickListener(this);
        tvName = (TextView) view.findViewById(R.id.txt_name);
        edtName = (EditText) view.findViewById(R.id.edt_name);
        edtAddress = (EditText) view.findViewById(R.id.edt_address);
        edtEmail = (EditText) view.findViewById(R.id.edt_email);
        edtPhone = (EditText) view.findViewById(R.id.edt_phone);
        edtIntroduct = (EditText) view.findViewById(R.id.edt_introduct);
        edtWeb = (EditText) view.findViewById(R.id.edt_website);
        nameWrapper = (TextInputLayout) view.findViewById(R.id.nameWrapper);
        addressWrapper = (TextInputLayout) view.findViewById(R.id.addressWrapper);
        emailWrapper = (TextInputLayout) view.findViewById(R.id.emailWrapper);
        phoneWrapper = (TextInputLayout) view.findViewById(R.id.phoneWrapper);
//        tvNameError = (TextView) view.findViewById(R.id.txt_name_error);
//        tvAddressError = (TextView) view.findViewById(R.id.txt_address_error);
//        tvEmailError = (TextView) view.findViewById(R.id.txt_email_error);
//        tvPhoneError = (TextView) view.findViewById(R.id.txt_phone_error);
        setUserInfor(u);
        btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
        btnLogOut = (Button) view.findViewById(R.id.btn_log_out_accout);
        btnLogOut.setOnClickListener(this);
        return view;
    }
    public void clearText() {

            tvName.setText("");
            edtName.setText("");
            edtAddress.setText("");
            edtEmail.setText("");
            edtPhone.setText("");
            edtIntroduct.setText("");
            edtWeb.setText("");

    }
    public void setUserInfor(User user) {
        if (user != null) {
            tvName.setText(user.getUsername());
            imvAvatar.setImageURI(null);
            Picasso.with(getContext()).load(u.getAvatar()).into(imvAvatar);
            edtName.setText(user.getName());
            edtAddress.setText(user.getAddress());
            edtEmail.setText(user.getEmail());
            edtPhone.setText(user.getPhone());
            edtIntroduct.setText(user.getIntroduce());
            edtWeb.setText(user.getWebsite());
        }
    }


    public boolean isValid() {
        boolean flag = true;
        String name =edtName.getText().toString().trim();
        String address =edtAddress.getText().toString().trim();
        String email =edtEmail.getText().toString().trim();
        String phone =edtPhone.getText().toString().trim();
//        if (edtName.getText().toString().trim().length()<3 || edtName.getText().toString().trim().length()>25) {
//            flag = false;
//            tvNameError.setError("hahaha");
//            tvNameError.setVisibility(View.VISIBLE);
//        } else {
//            tvNameError.setVisibility(View.GONE);
//        }
        if(name.length()<3 || name.length()>25 ){
            flag=false;
            edtName.setText("");
            nameWrapper.setError("Độ dài tên không hợp lệ.");
        } else{
            nameWrapper.setError("");
            nameWrapper.setErrorEnabled(false);
        }

        if(address.length()<8 || address.length()>25 ){
            flag=false;
            edtAddress.setText("");
            addressWrapper.setError("Độ dài địa chỉ không hợp lệ.");
        } else{
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
            phoneWrapper.setError("");
            phoneWrapper.setErrorEnabled(false);
        }


//        if (edtAddress.getText().toString().trim().isEmpty()) {
//            flag = false;
//            tvAddressError.setVisibility(View.VISIBLE);
//        } else {
//            tvAddressError.setVisibility(View.GONE);
//        }
//
//        if (edtEmail.getText().toString().trim().isEmpty()) {
//            flag = false;
//            tvEmailError.setVisibility(View.VISIBLE);
//        } else {
//            tvEmailError.setVisibility(View.GONE);
//        }
//
//        if (edtPhone.getText().toString().trim().isEmpty()) {
//            flag = false;
//            tvPhoneError.setVisibility(View.VISIBLE);
//        } else {
//            tvPhoneError.setVisibility(View.GONE);
//        }


        return flag;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_avatar:
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1)
                        .start(getContext(), this);
                break;
            case R.id.btn_edit_password:
                //Todo sửa thành user password
                ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(getContext(), u.getPassword());
                changePasswordDialog.setTitle("Đổi Mật Khẩu");
                changePasswordDialog.setCanceledOnTouchOutside(true);
                changePasswordDialog.show();
                break;
            case R.id.btn_confirm:
                if (isValid()) {
                    showLoading();
                    //TODO thực hiện add
                    User user = CoreManager.getUser(getContext());
                    user.setName(edtName.getText().toString().trim());
                    user.setAddress(edtAddress.getText().toString().trim());
                    user.setEmail(edtEmail.getText().toString().trim());
                    user.setPhone(edtPhone.getText().toString().trim());
                    user.setIntroduce(edtIntroduct.getText().toString().trim());
                    user.setWebsite(edtWeb.getText().toString().trim());
                    UserService userService = APIServiceManager.getUserService();
                    userService.update(user.getId(),user).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                User userTemplate = CoreManager.getUser(getContext());
                                userTemplate.setName(edtName.getText().toString().trim());
                                userTemplate.setAddress(edtAddress.getText().toString().trim());
                                userTemplate.setEmail(edtEmail.getText().toString().trim());
                                userTemplate.setPhone(edtPhone.getText().toString().trim());
                                userTemplate.setIntroduce(edtIntroduct.getText().toString().trim());
                                userTemplate.setWebsite(edtWeb.getText().toString().trim());
                                CoreManager.setUser(getContext(),userTemplate);
                                Toast.makeText(getContext(), "Update User Success",
                                        Toast.LENGTH_LONG).show();

                                closeLoading();
                            } else {
                                Toast.makeText(getContext(), "Update User Fail, please try again!",
                                        Toast.LENGTH_LONG).show();
                                    try {
                                        Log.e("LOG_ERROR", response.errorBody().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                closeLoading();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getContext(), "Update user error, please try again!",
                                    Toast.LENGTH_LONG).show();
                            closeLoading();
                            Log.e("LOG_ERROR","fai" +t.getMessage());
                        }
                    });
                }
                break;
            case R.id.btn_log_out_accout:
                showConfirmLogoutDialog();
                break;
        }
    }

    public void showConfirmLogoutDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Đăng xuất").setMessage(R.string.confirm_log_out_message)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCallback.destoyFragmentUser();

                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       dialog.dismiss();
                    }
                });
        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    InputStream is = getActivity().getContentResolver().openInputStream(resultUri);
                    uploadImage(getBytes(is));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imvAvatar.setImageURI(resultUri);
                mCallback.changeAvatar(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getActivity(), error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        }


    }


    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    private void uploadImage(byte[] imageBytes) {

        FileService fileService = APIServiceManager.getFileService();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        MultipartBody.Part username = MultipartBody.Part.createFormData("username", CoreManager.getUser(getContext()).getUsername());
        //cột username đang bị null hết chỉ có 2 record dc add vào: luc2, luc12345678
        fileService.upload(body, username).enqueue(new Callback<ImgResponse>() {
            @Override
            public void onResponse(Call<ImgResponse> call, retrofit2.Response<ImgResponse> response) {
                if (response.isSuccessful()) {
                    ImgResponse im = response.body();
                    Log.e("TAG", "SUCCESS " + im.toString());
                    //Todo set link avtar for core
                    CoreManager.getUser(getContext()).setAvatar(im.getData());
                    Toast.makeText(getContext(), "Update Avatar success!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Log.e("TAG", "ELSE" + response.errorBody().toString() + response.code());
                    Toast.makeText(getContext(), "Update Avatar Fail",
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ImgResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Update Avatar error, please try again!",
                        Toast.LENGTH_LONG).show();
                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }


}
