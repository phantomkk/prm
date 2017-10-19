package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lucky on 17-Oct-17.
 */

public interface CategoryService {
    @GET("/api/categories/{id}/products/")
    Call<List<Product>> getProductByCategoryId(@Path("id") Integer id);
}
