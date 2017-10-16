package com.project.barcodechecker.api;

import com.project.barcodechecker.api.services.CommentService;
import com.project.barcodechecker.api.services.ProductService;
import com.project.barcodechecker.api.services.UserService;
import com.project.barcodechecker.utils.AppConst;

/**
 * Created by lucky on 13-Sep-17.
 */

public class APIServiceManager {
    private static final String getURL(){
//        if(Utils.isEmulator()){
//            return "http://10.0.2.2:49860/";
//        }else{
            return AppConst.SERVER_NAME;
//        }
    }
    public static ProductService getPService(){
        return RetrofitClient.getClient(getURL()).create(ProductService.class);
    }
    public static UserService getUserService(){
        return RetrofitClient.getClient(getURL()).create(UserService.class);
    }
    public static CommentService getCommentService(){
        return RetrofitClient.getClient(getURL()).create(CommentService.class);
    }
}
