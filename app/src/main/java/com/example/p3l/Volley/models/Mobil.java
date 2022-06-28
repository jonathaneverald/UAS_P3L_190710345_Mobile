package com.example.p3l.Volley.models;

public class Mobil {
    private long id_mobil_sewa;
    private String nama_mobil_sewa;
    private String tipe_mobil_sewa;
    private String jenis_transmisi;
    private String jenis_bahan_bakar;
    private String warna_mobil_sewa;
    private String volume_bagasi_mobil;
    private String fasilitas_mobil;
    private String foto_mobil;
    private double harga_sewa_mobil;

    public Mobil(long id_mobil, String nama_mobil_sewa, String tipe_mobil_sewa, String jenis_transmisi, String jenis_bahan_bakar, String warna_mobil_sewa, String volume_bagasi_mobil, String fasilitas_mobil, String foto_mobil, double harga_sewa_mobil) {
        this.id_mobil_sewa = id_mobil;
        this.nama_mobil_sewa = nama_mobil_sewa;
        this.tipe_mobil_sewa = tipe_mobil_sewa;
        this.jenis_transmisi = jenis_transmisi;
        this.jenis_bahan_bakar = jenis_bahan_bakar;
        this.warna_mobil_sewa = warna_mobil_sewa;
        this.volume_bagasi_mobil = volume_bagasi_mobil;
        this.fasilitas_mobil = fasilitas_mobil;
        this.foto_mobil = foto_mobil;
        this.harga_sewa_mobil = harga_sewa_mobil;
    }

    public long getId_mobil() {
        return id_mobil_sewa;
    }

    public void setId_mobil(long id_mobil_sewa) {
        this.id_mobil_sewa = id_mobil_sewa;
    }

    public String getNama_mobil_sewa() {
        return nama_mobil_sewa;
    }

    public void setNama_mobil_sewa(String nama_mobil_sewa) {
        this.nama_mobil_sewa = nama_mobil_sewa;
    }

    public String getTipe_mobil() {
        return tipe_mobil_sewa;
    }

    public void setTipe_mobil(String tipe_mobil_sewa) {
        this.tipe_mobil_sewa = tipe_mobil_sewa;
    }

    public String getJenis_transmisi() {
        return jenis_transmisi;
    }

    public void setJenis_transmisi(String jenis_transmisi) {
        this.jenis_transmisi = jenis_transmisi;
    }

    public String getJenis_bahan_bakar() {
        return jenis_bahan_bakar;
    }

    public void setJenis_bahan_bakar(String jenis_bahan_bakar) {
        this.jenis_bahan_bakar = jenis_bahan_bakar;
    }

    public String getWarna_mobil() {
        return warna_mobil_sewa;
    }

    public void setWarna_mobil(String warna_mobil_sewa) {
        this.warna_mobil_sewa = warna_mobil_sewa;
    }

    public String getVolume_bagasi() {
        return volume_bagasi_mobil;
    }

    public void setVolume_bagasi(String volume_bagasi_mobil) {
        this.volume_bagasi_mobil = volume_bagasi_mobil;
    }

    public String getFasilitas() {
        return fasilitas_mobil;
    }

    public void setFasilitas(String fasilitas_mobil) {
        this.fasilitas_mobil = fasilitas_mobil;
    }

    public String getFoto_mobil() {
        return foto_mobil;
    }

    public void setFoto_mobil(String gambar_mobil) {
        this.foto_mobil = foto_mobil;
    }

    public double getHarga_sewa() {
        return harga_sewa_mobil;
    }

    public void setHarga_sewa(double harga_sewa_mobil) {
        this.harga_sewa_mobil = harga_sewa_mobil;
    }
}
