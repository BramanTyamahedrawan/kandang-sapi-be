package com.ternak.sapi.payload;

public class JenisVaksinRequest {

    private String idJenisVaksin;
    private String namaVaksin;
    private String deskripsi;

    public JenisVaksinRequest() {
    }

    public JenisVaksinRequest(String idJenisVaksin, String namaVaksin, String deskripsi) {
        this.idJenisVaksin = idJenisVaksin;
        this.namaVaksin = namaVaksin;
        this.deskripsi = deskripsi;
    }

    public String getIdJenisVaksin() {
        return idJenisVaksin;
    }

    public void setIdJenisVaksin(String idJenisVaksin) {
        this.idJenisVaksin = idJenisVaksin;
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

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idJenisVaksin":
                this.idJenisVaksin = value;
                break;
            case "namaVaksin":
                this.namaVaksin = value;
                break;
            case "deskripsi":
                this.deskripsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
