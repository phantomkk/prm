package com.project.barcodechecker.fragments;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.project.barcodechecker.R;
import com.project.barcodechecker.activities.MainActivity;
import com.project.barcodechecker.activities.TestActivity;
import com.project.barcodechecker.api.APIServiceManager;
import com.project.barcodechecker.api.services.FileService;
import com.project.barcodechecker.models.ImgResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Lenovo on 21/10/2017.
 */

public class AccoutFragment extends Fragment implements View.OnClickListener {
    public AccoutFragment() {
        // Required empty public constructor
    }

    ImageButton btnChangeAvarta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accout, container, false);
        btnChangeAvarta = (ImageButton) view.findViewById(R.id.btn_change_avatar);
        btnChangeAvarta.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_avatar:
                showDiaglogEditAvatar();
                break;
        }
    }

    public void showDiaglogEditAvatar() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Bạn muốn cập nhật ảnh đại diện bằng?")
                .setPositiveButton("Camera", positiveListener)
                .setNegativeButton("Album Ảnh", negativeListener)
                .show();
    }

    public static final int EDIT_AVATAR_BY_CAMERA = 102;
    public static final int EDIT_AVATAR_BY_ALBUM = 103;
    public static final int INTENT_REQUEST_CODE = 101;
    final DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivityForResult(new Intent(getActivity(), TestActivity.class), EDIT_AVATAR_BY_CAMERA);
        }
    };

    final DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");

            try {
                startActivityForResult(intent, EDIT_AVATAR_BY_ALBUM);

            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == INTENT_REQUEST_CODE) {
                try {

                    InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
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
