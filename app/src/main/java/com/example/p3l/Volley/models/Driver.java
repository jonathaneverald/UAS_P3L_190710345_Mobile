package com.example.p3l.Volley.models;

public class Driver {
    private long id_driver;
    private String format_id,
            nama_driver,
            alamat_driver,
            tanggal_lahir,
            jenis_kelamin,
            email,
            no_telp,
            bahasa,
            pas_foto,
            sim,
            surat_bebas_napza,
            surat_kesehatan_jiwa,
            surat_kesehatan_jasmani,
            skck,
            status_driver,
            status_dokumen;
    private double tarif_driver, rerata_rating;

    public Driver(long id_driver, String format_id, String nama_driver, String alamat_driver, String tanggal_lahir, String jenis_kelamin, String email, String no_telp, String bahasa, String pas_foto, String sim, String surat_bebas_napza, String surat_kesehatan_jiwa, String surat_kesehatan_jasmani, String skck, String status_driver, String status_dokumen, double tarif_driver, double rerata_rating) {
        this.id_driver = id_driver;
        this.format_id = format_id;
        this.nama_driver = nama_driver;
        this.alamat_driver = alamat_driver;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.email = email;
        this.no_telp = no_telp;
        this.bahasa = bahasa;
        this.pas_foto = pas_foto;
        this.sim = sim;
        this.surat_bebas_napza = surat_bebas_napza;
        this.surat_kesehatan_jiwa = surat_kesehatan_jiwa;
        this.surat_kesehatan_jasmani = surat_kesehatan_jasmani;
        this.skck = skck;
        this.status_driver = status_driver;
        this.status_dokumen = status_dokumen;
        this.tarif_driver = tarif_driver;
        this.rerata_rating = rerata_rating;
    }

    public long getId_driver() {
        return id_driver;
    }

    public void setId_driver(long id_driver) {
        this.id_driver = id_driver;
    }

    public String getFormat_id() {
        return format_id;
    }

    public void setFormat_id(String format_id) {
        this.format_id = format_id;
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
    }

    public String getAlamat_driver() {
        return alamat_driver;
    }

    public void setAlamat_driver(String alamat_driver) {
        this.alamat_driver = alamat_driver;
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

    public String getBahasa() {
        return bahasa;
    }

    public void setBahasa(String bahasa) {
        this.bahasa = bahasa;
    }

    public String getPas_foto() {
        return pas_foto;
    }

    public void setPas_foto(String pas_foto) {
        this.pas_foto = pas_foto;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getSurat_bebas_napza() {
        return surat_bebas_napza;
    }

    public void setSurat_bebas_napza(String surat_bebas_napza) {
        this.surat_bebas_napza = surat_bebas_napza;
    }

    public String getSurat_kesehatan_jiwa() {
        return surat_kesehatan_jiwa;
    }

    public void setSurat_kesehatan_jiwa(String surat_kesehatan_jiwa) {
        this.surat_kesehatan_jiwa = surat_kesehatan_jiwa;
    }

    public String getSurat_kesehatan_jasmani() {
        return surat_kesehatan_jasmani;
    }

    public void setSurat_kesehatan_jasmani(String surat_kesehatan_jasmani) {
        this.surat_kesehatan_jasmani = surat_kesehatan_jasmani;
    }

    public String getSkck() {
        return skck;
    }

    public void setSkck(String skck) {
        this.skck = skck;
    }

    public String getStatus_driver() {
        return status_driver;
    }

    public void setStatus_driver(String status_driver) {
        this.status_driver = status_driver;
    }

    public String getStatus_dokumen() {
        return status_dokumen;
    }

    public void setStatus_dokumen(String status_dokumen) {
        this.status_dokumen = status_dokumen;
    }

    public double getTarif_driver() {
        return tarif_driver;
    }

    public void setTarif_driver(double tarif_driver) {
        this.tarif_driver = tarif_driver;
    }

    public double getRerata_rating() {
        return rerata_rating;
    }

    public void setRerata_rating(double rerata_rating) {
        this.rerata_rating = rerata_rating;
    }
}
