package com.project.barcodechecker.api.services;

import com.project.barcodechecker.api.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lucky on 13-Sep-17.
 */

public interface ProductService {
    @GET("/api/products/")
    Call<List<Product>> getProducts();
    @GET("/api/products/{id}")
    Call<Product> getProductById(@Path("id") Integer id);

}
