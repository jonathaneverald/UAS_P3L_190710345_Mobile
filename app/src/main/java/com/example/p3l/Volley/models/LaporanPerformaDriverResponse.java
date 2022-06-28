package com.example.p3l.Volley.models;

public class LaporanPerformaDriverResponse {
    private String message;

    private LaporanPerformaDriver[] laporan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LaporanPerformaDriver[] getLaporanPerformaDriver() {
        return laporan;
    }

    public void setLaporanPerformaDriver(LaporanPerformaDriver[] laporan) {
        this.laporan = laporan;
    }
}
