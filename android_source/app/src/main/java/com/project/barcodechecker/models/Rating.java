package com.project.barcodechecker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lucky on 15-Oct-17.
 */

public class Rating implements Serializable {

    @SerializedName("ProductID")
    private int productID;
    @SerializedName("UserID")
    private int userID;
    @SerializedName("Rating1")
    private float rating;



    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
