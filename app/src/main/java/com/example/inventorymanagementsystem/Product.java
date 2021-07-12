package com.example.inventorymanagementsystem;

import java.io.Serializable;

public class Product implements Serializable {

    int productId;
    String imageURIString;
    String productName;
    int quantity;
    double pricePerUnit;

    boolean isChecked = false;


    public Product(int productId, String imageURIString, String productName, int quantity, double pricePerUnit) {

        this.productId = productId;
        this.imageURIString = imageURIString;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;

    }
}
