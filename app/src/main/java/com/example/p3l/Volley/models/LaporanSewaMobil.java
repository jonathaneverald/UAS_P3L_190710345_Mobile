package com.example.p3l.Volley.models;

public class LaporanSewaMobil {
    private String tipe_mobil_sewa, nama_mobil_sewa;
    private int jumlah_peminjaman;
    private double pendapatan;

    public LaporanSewaMobil(String tipe_mobil_sewa, String nama_mobil_sewa, int jumlah_peminjaman, double pendapatan) {
        this.tipe_mobil_sewa = tipe_mobil_sewa;
        this.nama_mobil_sewa = nama_mobil_sewa;
        this.jumlah_peminjaman = jumlah_peminjaman;
        this.pendapatan = pendapatan;
    }

    public String getTipe_mobil() {
        return tipe_mobil_sewa;
    }

    public void setTipe_mobil(String tipe_mobil_sewa) {
        this.tipe_mobil_sewa = tipe_mobil_sewa;
    }

    public String getNama_mobil_sewa() {
        return nama_mobil_sewa;
    }

    public void setNama_mobil_sewa(String nama_mobil_sewa) {
        this.nama_mobil_sewa = nama_mobil_sewa;
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
