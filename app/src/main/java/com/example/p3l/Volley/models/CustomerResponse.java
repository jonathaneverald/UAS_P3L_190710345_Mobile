package com.example.p3l.Volley.models;

import com.google.gson.annotations.SerializedName;

public class CustomerResponse {
    private String message;


    private Customer[] customer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Customer[] getCustomer() {
        return customer;
    }

    public void setCustomer(Customer[] customer) {
        this.customer = customer;
    }
}
