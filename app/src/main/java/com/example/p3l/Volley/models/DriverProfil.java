package com.example.p3l.Volley.models;

public class DriverProfil {
    private String alamat_driver,
            email,
            no_telp,
            bahasa,
            password;

    public DriverProfil(String alamat_driver, String email, String no_telp, String bahasa, String password) {
        this.alamat_driver = alamat_driver;
        this.email = email;
        this.no_telp = no_telp;
        this.bahasa = bahasa;
        this.password = password;
    }
}
