package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by lucky on 15-Oct-17.
 */

public interface UserService {
    @POST("/api/users/")
    Call<User> login(@Body User user);
}
