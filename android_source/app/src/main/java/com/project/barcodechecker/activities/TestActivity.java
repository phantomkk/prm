package com.project.barcodechecker.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.FileService;
import com.project.barcodechecker.api.services.SaleService;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.models.ImgResponse;
import com.project.barcodechecker.models.Sale;
import com.project.barcodechecker.models.User;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends BaseActivity {
    static int INTENT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        UserService us = APIServiceManager.getUserService();
        User u = new User();
        u.setId(1);
        u.setUsername("trinhvo");
        u.setName("trinhvo");
        u.setAddress("go vap tphcm");
        u.setEmail("mr.trinhvo1996@gmail.com");
        u.setPhone("19338383838");
        u.setIntroduce("193939399009");
        u.setWebsite("trinhvo.com");
        us.update(1, u).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    logError("SUCCESS");

                } else {
                    try {
                        logErrorBody(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                logError(t.getMessage());
            }
        });
//        SaleService
//                 ss = APIServiceManager.getSaleService();
//        ss.getSaleByProductID(66).enqueue(new Callback<List<Sale>>() {
//            @Override
//            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
//                int a = 1;
//            }
//
//            @Override
//            public void onFailure(Call<List<Sale>> call, Throwable t) {
//int a =1;
//            }
//        });
////        Picasso.with(this).load("https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt-small.jpg").into((ImageView)findViewById(R.id.img_test));
//        ((Button) findViewById(R.id.btn_choose_img)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
////                intent.setType("image/jpeg");
////
////                try {
////                    startActivityForResult(intent, INTENT_REQUEST_CODE);
////
////                } catch (ActivityNotFoundException e) {
////
////                    e.printStackTrace();
////                }
//////                setResult(RESULT_OK);
//////                finish();
//
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1)
//                        .start(TestActivity.this);
//            }
//        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_test;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == INTENT_REQUEST_CODE) {
//
//            if (resultCode == RESULT_OK) {
//
//                try {
//
//                    InputStream is = getContentResolver().openInputStream(data.getData());
//
//                    uploadImage(getBytes(is));
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
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

    private String mImageUrl = "";

    private void uploadImage(byte[] imageBytes) {

        FileService fileService = APIServiceManager.getFileService();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        MultipartBody.Part username = MultipartBody.Part.createFormData("username", "luc2");
        //cột username đang bị null hết chỉ có 2 record dc add vào: luc2, luc12345678
        fileService.upload(body, username).enqueue(new Callback<ImgResponse>() {
            @Override
            public void onResponse(Call<ImgResponse> call, retrofit2.Response<ImgResponse> response) {
                if (response.isSuccessful()) {
                    ImgResponse im = response.body();
                    Log.e("TAG", "SUCCESS " + im.toString());
                } else {
                    Log.e("TAG", "ELSE" + response.errorBody().toString() + response.code());

                }
            }

            @Override
            public void onFailure(Call<ImgResponse> call, Throwable t) {

                Log.d("TAG", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
