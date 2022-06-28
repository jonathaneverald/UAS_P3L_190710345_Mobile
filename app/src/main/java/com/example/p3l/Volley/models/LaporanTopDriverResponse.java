package com.example.p3l.Volley.models;

public class LaporanTopDriverResponse {
    private String message;

    private LaporanTopDriver[] laporan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LaporanTopDriver[] getLaporanTopDriver() {
        return laporan;
    }

    public void setLaporanTopDriver(LaporanTopDriver[] laporan) {
        this.laporan = laporan;
    }
}
