package com.example.p3l.Volley.models;

public class Transaksi {
    private long id_transaksi, id_customer, id_mobil_sewa, id_driver, id_promo;
    private Mobil mobil;
    private Driver driver;
    private String jenis_penyewaan_mobil;
    private String tanggal_transaksi_sewa_mobil, status_transaksi;
    private double total_transaksi;

    public Transaksi(long id_transaksi, long id_customer, long id_mobil_sewa, long id_driver, long id_promo, Mobil mobil, Driver driver, String jenis_penyewaan_mobil, String tanggal_transaksi_sewa_mobil, String status_transaksi, double total_transaksi) {
        this.id_transaksi = id_transaksi;
        this.id_customer = id_customer;
        this.id_mobil_sewa = id_mobil_sewa;
        this.id_driver = id_driver;
        this.id_promo = id_promo;
        this.mobil = mobil;
        this.driver = driver;
        this.jenis_penyewaan_mobil = jenis_penyewaan_mobil;
        this.tanggal_transaksi_sewa_mobil = tanggal_transaksi_sewa_mobil;
        this.status_transaksi = status_transaksi;
        this.total_transaksi = total_transaksi;
    }

    public long getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(long id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public long getId_mobil() {
        return id_mobil_sewa;
    }

    public void setId_mobil(long id_mobil_sewa) {
        this.id_mobil_sewa = id_mobil_sewa;
    }

    public long getId_driver() {
        return id_driver;
    }

    public void setId_driver(long id_driver) {
        this.id_driver = id_driver;
    }

    public long getId_promo() {
        return id_promo;
    }

    public void setId_promo(long id_promo) {
        this.id_promo = id_promo;
    }

    public Mobil getMobil() {
        return mobil;
    }

    public void setMobil(Mobil mobil) {
        this.mobil = mobil;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getJenis_penyewaan() {
        return jenis_penyewaan_mobil;
    }

    public void setJenis_penyewaan(String jenis_penyewaan_mobil) {
        this.jenis_penyewaan_mobil = jenis_penyewaan_mobil;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi_sewa_mobil;
    }

    public void setTanggal_transaksi(String tanggal_transaksi_sewa_mobil) {
        this.tanggal_transaksi_sewa_mobil = tanggal_transaksi_sewa_mobil;
    }

    public String getStatus_transaksi() {
        return status_transaksi;
    }

    public void setStatus_transaksi(String status_transaksi) {
        this.status_transaksi = status_transaksi;
    }

    public double getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(double total_transaksi) {
        this.total_transaksi = total_transaksi;
    }
}
