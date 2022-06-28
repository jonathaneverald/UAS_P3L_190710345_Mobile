package com.example.p3l.Volley.models;

public class LaporanDetailPendapatanResponse {
    private String message;

    private LaporanDetailPendapatan[] laporan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LaporanDetailPendapatan[] getLaporanDetailPendapatan() {
        return laporan;
    }

    public void setLaporanDetailPendapatan(LaporanDetailPendapatan[] laporan) {
        this.laporan = laporan;
    }
}
