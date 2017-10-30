package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lucky on 15-Oct-17.
 */

public interface UserService {
    @POST("/api/users/login")
    Call<User> login(@Body User user);
    @POST("/api/users/register")
    Call<User> register(@Body User user);
    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") Integer id);
}
