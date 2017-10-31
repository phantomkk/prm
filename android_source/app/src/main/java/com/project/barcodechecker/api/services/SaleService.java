package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Lenovo on 30/10/2017.
 */

public interface SaleService {
    @GET("/api/products/{id}/sales")
    Call<List<Sale>> getSaleByProductID(@Path("id") Integer id);
    @GET("/api/users/{id}/sales")
    Call<List<Sale>> getSaleByUserID(@Path("id") Integer id);
}
