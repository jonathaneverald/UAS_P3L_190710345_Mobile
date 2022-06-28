package com.example.p3l.Volley.models;

public class CustomerProfil {
    private String alamat_customer,
            email,
            no_telp,
            password;

    public CustomerProfil(String alamat_customer, String email, String no_telp, String password) {
        this.alamat_customer = alamat_customer;
        this.email = email;
        this.no_telp = no_telp;
        this.password = password;
    }
}
