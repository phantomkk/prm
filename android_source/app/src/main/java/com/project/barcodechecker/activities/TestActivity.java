package com.project.barcodechecker.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.barcodechecker.R;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.FileService;
import com.project.barcodechecker.models.ImgResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class TestActivity extends Activity {
    static int INTENT_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        Picasso.with(this).load("https://www.smashingmagazine.com/wp-content/uploads/2015/06/10-dithering-opt-small.jpg").into((ImageView)findViewById(R.id.img_test));
        ((Button) findViewById(R.id.btn_choose_img)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");

                try {
                    startActivityForResult(intent, INTENT_REQUEST_CODE);

                } catch (ActivityNotFoundException e) {

                    e.printStackTrace();
                }

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                try {

                    InputStream is = getContentResolver().openInputStream(data.getData());

                    uploadImage(getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                    Log.e("TAG", "SUCCESS " + im.toString() );
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
