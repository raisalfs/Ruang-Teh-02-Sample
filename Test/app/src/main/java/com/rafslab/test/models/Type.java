package com.rafslab.test.models;

import java.io.Serializable;

public class Type implements Serializable {
    private String value;
    private String price;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
