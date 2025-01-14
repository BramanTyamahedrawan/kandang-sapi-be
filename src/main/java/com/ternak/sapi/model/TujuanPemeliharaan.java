package com.ternak.sapi.model;

public class TujuanPemeliharaan {
    private String idTujuanPemeliharaan;
    private String tujuanPemeliharaan;
    private String deskripsi;

    public TujuanPemeliharaan() {
    }

    public TujuanPemeliharaan(String idTujuanPemeliharaan, String tujuanPemeliharaan, String deskripsi) {
        this.idTujuanPemeliharaan = idTujuanPemeliharaan;
        this.tujuanPemeliharaan = tujuanPemeliharaan;
        this.deskripsi = deskripsi;
    }

    public String getIdTujuanPemeliharaan() {
        return idTujuanPemeliharaan;
    }

    public void setIdTujuanPemeliharaan(String idTujuanPemeliharaan) {
        this.idTujuanPemeliharaan = idTujuanPemeliharaan;
    }

    public String getTujuanPemeliharaan() {
        return tujuanPemeliharaan;
    }

    public void setTujuanPemeliharaan(String tujuanPemeliharaan) {
        this.tujuanPemeliharaan = tujuanPemeliharaan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public boolean isValid() {
        return this.idTujuanPemeliharaan != null  && this.tujuanPemeliharaan != null && this.deskripsi != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idTujuanPemeliharaan":
                this.idTujuanPemeliharaan = value;
                break;
            case "tujuanPemeliharaan":
                this.tujuanPemeliharaan = value;
                break;
            case "deskripsi":
                this.deskripsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
