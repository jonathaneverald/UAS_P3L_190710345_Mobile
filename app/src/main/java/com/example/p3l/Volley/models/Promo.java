package com.example.p3l.Volley.models;

public class Promo {
    private long id_promo;
    private String kode_promo, jenis_promo, keterangan, status_promo;
    private double potongan_promo;

    public Promo(long id_promo, String kode_promo, String jenis_promo, String keterangan, String status_promo, double potongan_promo) {
        this.id_promo = id_promo;
        this.kode_promo = kode_promo;
        this.jenis_promo = jenis_promo;
        this.keterangan = keterangan;
        this.status_promo = status_promo;
        this.potongan_promo = potongan_promo;
    }

    public long getId_promo() {
        return id_promo;
    }

    public void setId_promo(long id_promo) {
        this.id_promo = id_promo;
    }

    public String getKode_promo() {
        return kode_promo;
    }

    public void setKode_promo(String kode_promo) {
        this.kode_promo = kode_promo;
    }

    public String getJenis_promo() {
        return jenis_promo;
    }

    public void setJenis_promo(String jenis_promo) {
        this.jenis_promo = jenis_promo;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus_promo() {
        return status_promo;
    }

    public void setStatus_promo(String status_promo) {
        this.status_promo = status_promo;
    }

    public double getPotongan_promo() {
        return potongan_promo;
    }

    public void setPotongan_promo(double potongan_promo) {
        this.potongan_promo = potongan_promo;
    }
}
