package com.project.barcodechecker.models;

import java.io.Serializable;

/**
 * Created by Lenovo on 15/10/2017.
 */

public class History implements Serializable {
    private int id;
    private String productName, productCode;
    private String Datetime;

    public History(String productName, String productCode) {
        this.productName = productName;
        this.productCode = productCode;
    }

    public History() {
    }

    public String getProductName() {
        return productName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDatetime() {
        return Datetime;
    }

    public void setDatetime(String datetime) {
        Datetime = datetime;
    }
}
