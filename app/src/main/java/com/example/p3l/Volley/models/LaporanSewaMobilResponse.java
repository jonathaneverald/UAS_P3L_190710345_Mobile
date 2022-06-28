package com.example.p3l.Volley.models;

public class LaporanSewaMobilResponse {
    private String message;

    private LaporanSewaMobil[] laporan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LaporanSewaMobil[] getLaporanSewaMobil() {
        return laporan;
    }

    public void setLaporanSewaMobil(LaporanSewaMobil[] laporan) {
        this.laporan = laporan;
    }
}
