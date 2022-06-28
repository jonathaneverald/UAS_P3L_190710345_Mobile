package com.example.p3l.Volley.models;

public class PegawaiResponse {
    private String message;

    private Pegawai[] pegawai;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Pegawai[] getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai[] pegawai) {
        this.pegawai = pegawai;
    }
}
