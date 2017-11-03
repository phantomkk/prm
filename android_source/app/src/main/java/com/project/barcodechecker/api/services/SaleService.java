package com.project.barcodechecker.api.services;

import com.project.barcodechecker.models.Product;
import com.project.barcodechecker.models.Sale;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Lenovo on 30/10/2017.
 */

public interface SaleService {
    @GET("/api/products/{id}/sales")
    Call<List<Sale>> getSaleByProductID(@Path("id") Integer id);
    @GET("/api/users/{id}/sales")
    Call<List<Sale>> getSaleByUserID(@Path("id") Integer id);
    @GET("/api/sales/{userID}/{productID}")
    Call<Sale> getSale(@Path("userID") int userID, @Path("productID") int productID);
    @POST("/api/sales/")
    Call<Sale> addSale(@Body Sale sale);
    @PUT("/api/sales/{userID}/{productID}")
    Call<Sale> editSale(@Path("userID") int userID, @Path("productID") int productID, @Body Sale sale);
    @DELETE("/api/sales/{userID}/{productID}")
    Call<Sale> deleteSale(@Path("userID") int userID, @Path("productID") int productID);

}
