package com.project.barcodechecker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Lenovo on 30/10/2017.
 */

public class Sale implements Serializable {
    @SerializedName("UserID")
    private int userId;
    @SerializedName("ProductID")
    private int productId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private float price;
    @SerializedName("DateCreate")
    private String dateCreate;
    @SerializedName("IsDelete")
    private boolean isDelete;
    @SerializedName("ImagePath")
    private String imagePath;

    public Sale(String name, float price, String dateCreate, boolean isDelete, String imagePath) {
        this.name = name;
        this.price = price;
        this.dateCreate = dateCreate;
        this.isDelete = isDelete;
        this.imagePath = imagePath;
    }

    public Sale(String name, float price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public Sale(int userId, String name, float price, String dateCreate, boolean isDelete, String imagePath) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.dateCreate = dateCreate;
        this.isDelete = isDelete;
        this.imagePath = imagePath;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
