package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.project.barcodechecker.models.Product;

/**
 * Created by lucky on 13-Sep-17.
 */

public interface ProductService {
    @GET("/api/products/")
    Call<List<Product>> getProducts();
    @GET("/api/products/{id}")
    Call<Product> getProductById(@Path("id") Integer id);
    @GET("/api/products/code/{code}")
    Call<Product> getProductById(@Path("code") String code);
//    @GET("/api/products/category/{id}")
//    Call<Product> getProductByCategoryId(@Path("id") Integer id);
}
