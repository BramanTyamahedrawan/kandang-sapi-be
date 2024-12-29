package com.ternak.sapi.payload;

import com.ternak.sapi.model.Peternak;

public class KandangRequest {
    private String idKandang;
    private String peternak_id;
    private String luas;
    private String jenisHewanId;
    private String kapasitas;
    private String jenisKandang;
    private String nilaiBangunan;
    private String alamat;
    private String namaKandang;
    private String latitude;
    private String longitude;
    private String nikPeternak;

    private Peternak peternak;

    public KandangRequest() {
    }

    public KandangRequest(String idKandang, String peternak_id, String luas, String jenisHewanId,
            String kapasitas,
            String jenisKandang, String nilaiBangunan, String alamat, String namaKandang, String jenisKandang1,
            String latitude, String longitude, String nikPeternak) {
        this.idKandang = idKandang;
        this.peternak_id = peternak_id;
        this.luas = luas;
        this.jenisHewanId = jenisHewanId;
        this.kapasitas = kapasitas;
        this.nikPeternak = nikPeternak;
        this.jenisKandang = jenisKandang;
        this.nilaiBangunan = nilaiBangunan;
        this.alamat = alamat;
        this.namaKandang = namaKandang;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // relasi peternak
    public Peternak getPeternak() {
        return peternak;
    }

    public void setPeternak(Peternak peternak) {
        this.peternak = peternak;
    }

    public String getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(String nikPeternak) {
        this.nikPeternak = nikPeternak;
    }

    public String getIdKandang() {
        return idKandang;
    }

    public void setIdKandang(String idKandang) {
        this.idKandang = idKandang;
    }

    public String getPeternak_id() {
        return peternak_id;
    }

    public void setPeternak_id(String peternak_id) {
        this.peternak_id = peternak_id;
    }

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    public String getJenisHewanId() {
        return jenisHewanId;
    }

    public void setJenisHewanId(String jenisHewanId) {
        this.jenisHewanId = jenisHewanId;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getJenisKandang() {
        return jenisKandang;
    }

    public void setJenisKandang(String jenisKandang) {
        this.jenisKandang = jenisKandang;
    }

    public String getNilaiBangunan() {
        return nilaiBangunan;
    }

    public void setNilaiBangunan(String nilaiBangunan) {
        this.nilaiBangunan = nilaiBangunan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getNamaKandang() {
        return namaKandang;
    }

    public void setNamaKandang(String namaKandang) {
        this.namaKandang = namaKandang;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKandang":
                this.idKandang = value;
                break;
            case "peternak_id":
                this.peternak_id = value;
                break;
            case "luas":
                this.luas = value;
                break;
            case "jenisHewanId":
                this.jenisHewanId = value;
                break;
            case "kapasitas":
                this.kapasitas = value;
                break;
            case "jenisKandang":
                this.jenisKandang = value;
                break;
            case "nilaiBangunan":
                this.nilaiBangunan = value;
                break;
            case "alamat":
                this.alamat = value;
                break;
            case "latitude":
                this.latitude = value;
                break;
            case "longitude":
                this.longitude = value;
                break;
            case "namaKandang":
                this.namaKandang = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}