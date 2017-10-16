package com.project.barcodechecker.models;


import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.project.barcodechecker.utils.AppConst;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by lucky on 12-Oct-17.
 */

public class Comment implements Serializable, Comparable<Comment>{

    @SerializedName("User")
    private User user;
    @SerializedName("ID")
    private int id;
    @SerializedName("UserID")
    private int userID;
    @SerializedName("ProductID")
    private int productID;
    @SerializedName("Comment1")
    private String comment;
    @SerializedName("DateCreated")
    private String dateCreated;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int compareTo(@NonNull Comment o) {
        return o.dateCreated.compareTo(dateCreated);
    }
}
