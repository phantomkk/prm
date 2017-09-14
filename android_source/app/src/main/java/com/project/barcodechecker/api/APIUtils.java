package com.project.barcodechecker.api;

import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.RetrofitClient;
import com.project.barcodechecker.utils.MyConstant;
import com.project.barcodechecker.utils.Utils;

import retrofit2.Retrofit;

/**
 * Created by lucky on 13-Sep-17.
 */

public class APIUtils {
    private static final String getURL(){
        if(Utils.isEmulator()){
            return "http://10.0.2.2:49860/";
        }else{
            return "http://192.168.1.4:49860/";
        }
    }
    public static ProductService getPService(){
        return RetrofitClient.getClient(getURL()).create(ProductService.class);
    }

}
