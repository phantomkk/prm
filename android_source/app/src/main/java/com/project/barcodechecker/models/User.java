package com.project.barcodechecker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lucky on 15-Oct-17.
 */

public class User implements Serializable {
    @SerializedName("ID")
    private int id;

    @SerializedName("Name")
    private String name;

    @SerializedName("Email")
    private String email;

    @SerializedName("Address")
    private String address;

    @SerializedName("Avatar")
    private String avatar;

    @SerializedName("Username")
    private String username;

    @SerializedName("Password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
