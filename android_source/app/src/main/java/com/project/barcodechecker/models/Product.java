package com.project.barcodechecker.models;

/**
 * Created by lucky on 13-Sep-17.
 */

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    public Product(String name, Double price, String imgDefault) {
        this.name = name;
        this.price = price;
        this.imgDefault = imgDefault;
    }

    public Product() {
    }

    private int idDatabase;
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("CategoryID")
    @Expose
    private int categoryID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Price")
    @Expose
    private Double price;
    @SerializedName("Country")
    @Expose
    private String country;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("ImgDefault")
    @Expose
    private String imgDefault;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("CategoryName")
    private String categoryName;
    @SerializedName("ComanyName")
    private String comanyName;
    @SerializedName("AverageRating")
    private double averageRating;

    public String toString() {
        return "id:  " + id + "\n" +
                "categoryID: " + categoryID + "\n" +
                "name: " + name + "\n" +
                "price: " + price + "\n" +
                "country: " + country + "\n" +
                "address: " + address + "\n" +
                "phone: " + phone + "\n" +
                "email: " + email + "\n" +
                "code: " + code + "\n" +
                "description: " + description + "\n";
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgDefault() {
        return imgDefault;
    }

    public void setImgDefault(String imgDefault) {
        this.imgDefault = imgDefault;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getComanyName() {
        return comanyName;
    }

    public void setComanyName(String comanyName) {
        this.comanyName = comanyName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getIdDatabase() {
        return idDatabase;
    }

    public void setIdDatabase(int idDatabase) {
        this.idDatabase = idDatabase;
    }
}
