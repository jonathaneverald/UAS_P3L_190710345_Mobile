package com.example.p3l.Volley.models;

import com.google.gson.annotations.SerializedName;

public class DriverResponse {
    private String message;

    @SerializedName("data")
    private Driver[] driver;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Driver[] getDriver() {
        return driver;
    }

    public void setDriver(Driver[] driver) {
        this.driver = driver;
    }
}
