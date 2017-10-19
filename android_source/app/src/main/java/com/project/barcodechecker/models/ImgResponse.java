package com.project.barcodechecker.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lucky on 18-Oct-17.
 */

public class ImgResponse implements Serializable{
    @SerializedName("Status")
    private int status;
    @SerializedName("Message")
    private String message;
    @SerializedName("Data")
    private String data;
    public String toString(){
        return "Status: " + status + "\n" +
          "Message: " + message + "\n" +
          "Data: " + data + "\n";
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
