package com.ternak.sapi.model;

public class JenisVaksin {
    private String idJenisVaksin;
    private String jenisVaksin;
    private String deskripsi;

    public JenisVaksin() {
    }

    public JenisVaksin(String idJenisVaksin, String jenisVaksin, String deskripsi) {
        this.idJenisVaksin = idJenisVaksin;
        this.jenisVaksin = jenisVaksin;
        this.deskripsi = deskripsi;
    }

    public String getIdJenisVaksin() {
        return idJenisVaksin;
    }

    public void setIdJenisVaksin(String idJenisVaksin) {
        this.idJenisVaksin = idJenisVaksin;
    }

    public String getJenisVaksin() {
        return jenisVaksin;
    }

    public void setJenisVaksin(String jenisVaksin) {
        this.jenisVaksin = jenisVaksin;
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
            case "jenisVaksin":
                this.jenisVaksin = value;
                break;
            case "deskripsi":
                this.deskripsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
