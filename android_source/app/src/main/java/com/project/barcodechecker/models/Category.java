package com.project.barcodechecker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lucky on 15-Oct-17.
 */

public class Category implements Serializable{
    @SerializedName("ID")
    private int id;
    @SerializedName("Name")
    private String name;
}
