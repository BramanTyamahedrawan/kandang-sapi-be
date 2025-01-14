package com.ternak.sapi.model;

public class NamaVaksin {
    private String idNamaVaksin;
    private String nama;
    private String deskripsi;

    private JenisVaksin jenisVaksin;

    public NamaVaksin() {
    }

    public NamaVaksin(String idNamaVaksin, String nama, String deskripsi, JenisVaksin jenisVaksin) {
        this.idNamaVaksin = idNamaVaksin;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.jenisVaksin = jenisVaksin;
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

    public JenisVaksin getJenisVaksin() {
        return jenisVaksin;
    }

    public void setJenisVaksin(JenisVaksin jenisVaksin) {
        this.jenisVaksin = jenisVaksin;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idNamaVaksin":
                this.idNamaVaksin = value;
                break;
            case "nama":
                this.nama = value;
                break;
            case "deskripsi":
                this.deskripsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
