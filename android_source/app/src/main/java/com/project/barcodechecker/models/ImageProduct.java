package com.project.barcodechecker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lucky on 15-Oct-17.
 */

public class ImageProduct implements Serializable {
    @SerializedName("ID")
    private int id;
    @SerializedName("ProductID")
    private int productID;
    @SerializedName("Path")
    private String path;
}
