package com.ternak.sapi.payload;

public class NamaVaksinRequest {
    private String idNamaVaksin;
    private String nama;
    private String deskripsi;
    private String idJenisVaksin;
    private String jenis;

    public NamaVaksinRequest() {
    }

    public NamaVaksinRequest(String idNamaVaksin, String nama, String deskripsi, String idJenisVaksin, String jenis) {
        this.idNamaVaksin = idNamaVaksin;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.idJenisVaksin = idJenisVaksin;
        this.jenis = jenis;
    }

    public String getIdNamaVaksin() {
        return idNamaVaksin;
    }

    public void setIdNamaVaksin(String idNamaVaksin) {
        this.idNamaVaksin = idNamaVaksin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getIdJenisVaksin() {
        return idJenisVaksin;
    }

    public void setIdJenisVaksin(String idJenisVaksin) {
        this.idJenisVaksin = idJenisVaksin;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
