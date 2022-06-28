package com.example.p3l.Volley.models;

public class LaporanTopCustomerResponse {
    private String message;

    private LaporanTopCustomer[] laporan;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LaporanTopCustomer[] getLaporanTopCustomer() {
        return laporan;
    }

    public void setLaporanTopCustomer(LaporanTopCustomer[] laporan) {
        this.laporan = laporan;
    }
}
