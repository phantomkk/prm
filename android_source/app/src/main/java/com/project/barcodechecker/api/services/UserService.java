package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by lucky on 15-Oct-17.
 */
public interface UserService {
    @POST("/api/users/login")
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);
    @POST("/api/users/register")
    Call<User> register(@Body User user);
    @GET("/api/users/{id}")
    Call<User> getUserById(@Path("id") Integer id);
    @GET("/api/users/{id}")
    Call<User> getByID(@Path("id") int id);
    @PUT("/api/users/{id}")
    Call<User> update(@Path("id") int id, @Body User user);
    @FormUrlEncoded
    @POST("/api/users/updatepwd")
    Call<User> updatePassword(@Field("id") int id, @Field("password") String password);

}
