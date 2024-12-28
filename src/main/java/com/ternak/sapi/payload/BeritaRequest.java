package com.ternak.sapi.payload;

public class BeritaRequest {
    private String judul;
    private String tglPembuatan;
    private String isiBerita;
    private String pembuat;

    public BeritaRequest() {
    }

    public BeritaRequest(String judul, String tglPembuatan, String isiBerita, String pembuat) {
        this.judul = judul;
        this.tglPembuatan = tglPembuatan;
        this.isiBerita = isiBerita;
        this.pembuat = pembuat;
    }
    
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getTglPembuatan() {
        return tglPembuatan;
    }

    public void setTglPembuatan(String tglPembuatan) {
        this.tglPembuatan = tglPembuatan;
    }

    public String getIsiBerita() {
        return isiBerita;
    }

    public void setIsiBerita(String isiBerita) {
        this.isiBerita = isiBerita;
    }

    public String getPembuat() {
        return pembuat;
    }

    public void setPembuat(String pembuat) {
        this.pembuat = pembuat;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "judul":
                this.judul = value;
                break;
            case "tglPembuatan":
                this.tglPembuatan = value;
                break;
            case "isiBerita":
                this.isiBerita = value;
                break;
            case "pembuat":
                this.pembuat = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
