package com.ternak.sapi.payload;

public class NamaVaksinRequest {
    private String idNamaVaksin;
    private String namaVaksin;
    private String deskripsi;
    private String idJenisVaksin;

    public NamaVaksinRequest() {
    }

    public NamaVaksinRequest(String idNamaVaksin, String namaVaksin, String deskripsi, String idJenisVaksin) {
        this.idNamaVaksin = idNamaVaksin;
        this.namaVaksin = namaVaksin;
        this.deskripsi = deskripsi;
        this.idJenisVaksin = idJenisVaksin;
    }

    public String getIdNamaVaksin() {
        return idNamaVaksin;
    }

    public void setIdNamaVaksin(String idNamaVaksin) {
        this.idNamaVaksin = idNamaVaksin;
    }

    public String getNamaVaksin() {
        return namaVaksin;
    }

    public void setNamaVaksin(String namaVaksin) {
        this.namaVaksin = namaVaksin;
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
}
