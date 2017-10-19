package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Img;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by lucky on 18-Oct-17.
 */

public interface FileService {
    @Multipart
    @POST("api/upload/image/user/")
    Call<Img> upload(@Part MultipartBody.Part image);
}
