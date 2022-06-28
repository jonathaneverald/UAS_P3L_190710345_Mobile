package com.example.p3l.Volley.models;

public class LaporanPerformaDriver {
    private String format_id_driver, nama_driver;
    private int jumlah_peminjaman;
    private float rerata_rating;

    public LaporanPerformaDriver(String format_id_driver, String nama_driver, int jumlah_peminjaman, float rerata_rating) {
        this.format_id_driver = format_id_driver;
        this.nama_driver = nama_driver;
        this.jumlah_peminjaman = jumlah_peminjaman;
        this.rerata_rating = rerata_rating;
    }

    public String getFormat_id() {
        return format_id_driver;
    }

    public void setFormat_id(String format_id_driver) {
        this.format_id_driver = format_id_driver;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public int getJumlah_peminjaman() {
        return jumlah_peminjaman;
    }

    public void setJumlah_peminjaman(int jumlah_peminjaman) {
        this.jumlah_peminjaman = jumlah_peminjaman;
    }

    public float getRerata_rating() {
        return rerata_rating;
    }

    public void setRerata_rating(float rerata_rating) {
        this.rerata_rating = rerata_rating;
    }
}
