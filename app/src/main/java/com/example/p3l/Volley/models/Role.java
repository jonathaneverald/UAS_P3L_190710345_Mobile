package com.example.p3l.Volley.models;

public class Role {
    private long id;
    private String jabatan;

    public Role(long id, String jabatan) {
        this.id = id;
        this.jabatan = jabatan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
