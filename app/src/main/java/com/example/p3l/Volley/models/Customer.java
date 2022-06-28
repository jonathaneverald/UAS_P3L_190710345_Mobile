package com.example.p3l.Volley.models;

public class Customer {
    private long id_customer;
    private String format_id,
        nama_customer,
        alamat_customer,
        tanggal_lahir,
        jenis_kelamin,
        email,
        no_telp,
        tanda_pengenal,
        foto_sim,
        dokumen_persyaratan,
        status_dokumen;

    public Customer(long id_customer, String format_id, String nama_customer, String alamat_customer, String tanggal_lahir, String jenis_kelamin, String email, String no_telp, String tanda_pengenal, String foto_sim, String dokumen_persyaratan, String status_dokumen) {
        this.id_customer = id_customer;
        this.format_id = format_id;
        this.nama_customer = nama_customer;
        this.alamat_customer = alamat_customer;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.email = email;
        this.no_telp = no_telp;
        this.tanda_pengenal = tanda_pengenal;
        this.foto_sim = foto_sim;
        this.dokumen_persyaratan = dokumen_persyaratan;
        this.status_dokumen = status_dokumen;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public String getFormat_id() {
        return format_id;
    }

    public void setFormat_id(String format_id) {
        this.format_id = format_id;
    }

    public String getNama_customer() {
        return nama_customer;
    }

    public void setNama_customer(String nama_customer) {
        this.nama_customer = nama_customer;
    }

    public String getAlamat_customer() {
        return alamat_customer;
    }

    public void setAlamat_customer(String alamat_customer) {
        this.alamat_customer = alamat_customer;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getTanda_pengenal() {
        return tanda_pengenal;
    }

    public void setTanda_pengenal(String tanda_pengenal) {
        this.tanda_pengenal = tanda_pengenal;
    }

    public String getFoto_sim() {
        return foto_sim;
    }

    public void setFoto_sim(String foto_sim) {
        this.foto_sim = foto_sim;
    }

    public String getDokumen_persyaratan() {
        return dokumen_persyaratan;
    }

    public void setDokumen_persyaratan(String dokumen_persyaratan) {
        this.dokumen_persyaratan = dokumen_persyaratan;
    }

    public String getStatus_dokumen() {
        return status_dokumen;
    }

    public void setStatus_dokumen(String status_dokumen) {
        this.status_dokumen = status_dokumen;
    }
}
