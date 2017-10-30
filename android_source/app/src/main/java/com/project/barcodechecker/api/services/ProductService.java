package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Comment;
import com.project.barcodechecker.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;

/**
 * Created by lucky on 13-Sep-17.
 */

public interface ProductService {
    @GET("/api/products/")
    Call<List<Product>> getProducts();
    @GET("/api/products/{id}")
    Call<Product> getProductById(@Path("id") Integer id);
    @GET("/api/products/code/{code}")
    Call<Product> getProductByCode(@Path("code") String code);
    @GET("/api/products/{id}/comments")
    Call<List<Comment>> getProductComments(@Path("id") Integer id);
    @GET("/api/products/name/{name}")
    Call<List<Product>> searchProduct(@Path("name") String name);
    @GET("/api/products/{id}/sales")
    Call<List<Sale>> getProductSales(@Path("id") Integer id);
}
