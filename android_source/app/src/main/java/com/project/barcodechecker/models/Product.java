package com.project.barcodechecker.models;

/**
 * Created by lucky on 13-Sep-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

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

    public Product(Integer id, int categoryID, String name, Double price, String country, String address, String phone, String email, String code, String description) {
        this.id = id;
        this.categoryID = categoryID;
        this.name = name;
        this.price = price;
        this.country = country;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.code = code;
        this.description = description;
    }
    public Product(){

    }

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
        name = name;
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
}
