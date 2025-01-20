package com.ternak.sapi.model;

public class JenisVaksin {
    private String idJenisVaksin;
    private String jenis;
    private String deskripsi;

    public JenisVaksin() {
    }

    public JenisVaksin(String idJenisVaksin, String jenis, String deskripsi) {
        this.idJenisVaksin = idJenisVaksin;
        this.jenis = jenis;
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

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean isValid() {
        return this.jenis != null &&
                this.deskripsi != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idJenisVaksin":
                this.idJenisVaksin = value;
                break;
            case "jenis":
                this.jenis = value;
                break;
            case "deskripsi":
                this.deskripsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
