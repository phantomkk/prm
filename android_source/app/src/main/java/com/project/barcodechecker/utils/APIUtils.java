package com.project.barcodechecker.utils;

import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.RetrofitClient;

/**
 * Created by lucky on 13-Sep-17.
 */

public class APIUtils {
    private static final String getURL(){
//        if(Utils.isEmulator()){
//            return "http://10.0.2.2:49860/";
//        }else{
            return "http://api61984.azurewebsites.net/";
//        }
    }
    public static ProductService getPService(){
        return RetrofitClient.getClient(getURL()).create(ProductService.class);
    }

}
