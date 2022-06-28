package com.example.p3l.Volley.models;

public class LaporanTopCustomer {
    private String nama_customer;
    private int jumlah_peminjaman;

    public LaporanTopCustomer(String nama_customer, int jumlah_peminjaman) {
        this.nama_customer = nama_customer;
        this.jumlah_peminjaman = jumlah_peminjaman;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public int getJumlah_peminjaman() {
        return jumlah_peminjaman;
    }

    public void setJumlah_peminjaman(int jumlah_peminjaman) {
        this.jumlah_peminjaman = jumlah_peminjaman;
    }
}
