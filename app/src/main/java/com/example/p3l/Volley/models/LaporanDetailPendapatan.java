package com.example.p3l.Volley.models;

public class LaporanDetailPendapatan {
    private String nama_customer, nama_mobil_sewa, jenis_penyewaan_mobil;
    private int jumlah_peminjaman;
    private double pendapatan;

    public LaporanDetailPendapatan(String nama_customer, String nama_mobil_sewa, String jenis_penyewaan_mobil, int jumlah_peminjaman, double pendapatan) {
        this.nama_customer = nama_customer;
        this.nama_mobil_sewa = nama_mobil_sewa;
        this.jenis_penyewaan_mobil = jenis_penyewaan_mobil;
        this.jumlah_peminjaman = jumlah_peminjaman;
        this.pendapatan = pendapatan;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getNama_mobil_sewa() {
        return nama_mobil_sewa;
    }

    public void setNama_mobil_sewa(String nama_mobil_sewa) {
        this.nama_mobil_sewa = nama_mobil_sewa;
    }

    public String getJenis_penyewaan() {
        return jenis_penyewaan_mobil;
    }

    public void setJenis_penyewaan(String jenis_penyewaan_mobil) {
        this.jenis_penyewaan_mobil = jenis_penyewaan_mobil;
    }

    public int getJumlah_peminjaman() {
        return jumlah_peminjaman;
    }

    public void setJumlah_peminjaman(int jumlah_peminjaman) {
        this.jumlah_peminjaman = jumlah_peminjaman;
    }

    public double getPendapatan() {
        return pendapatan;
    }

    public void setPendapatan(double pendapatan) {
        this.pendapatan = pendapatan;
    }
}
