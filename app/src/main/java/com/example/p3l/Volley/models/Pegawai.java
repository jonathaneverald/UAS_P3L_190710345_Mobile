package com.example.p3l.Volley.models;

public class Pegawai {
    private long id_pegawai;
    private String nama_pegawai;
    private Role role;

    public Pegawai(long id_pegawai, String nama_pegawai, Role role) {
        this.id_pegawai = id_pegawai;
        this.nama_pegawai = nama_pegawai;
        this.role = role;
    }

    public long getId_pegawai() {
        return id_pegawai;
    }

    public void setId_pegawai(long id_pegawai) {
        this.id_pegawai = id_pegawai;
    }

    public String getNama_pegawai() {
        return nama_pegawai;
    }

    public void setNama_pegawai(String nama_pegawai) {
        this.nama_pegawai = nama_pegawai;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
