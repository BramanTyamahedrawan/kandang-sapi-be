package com.ternak.sapi.payload;

import com.ternak.sapi.model.JenisHewan;
import com.ternak.sapi.model.Peternak;

public class KandangRequest {
    private String idKandang;
    private String idPeternak;
    private String luas;

    private String kapasitas;
    private String jenisKandang;
    private String nilaiBangunan;
    private String alamat;
    private String namaKandang;
    private String latitude;
    private String longitude;
    private String nikPeternak;
    private String namaPeternak;
    private Peternak peternak;

    private JenisHewan jenisHewan;
    private String idJenisHewan;
    private String jenis;


    public KandangRequest() {
    }

    public KandangRequest(String idKandang, String idPeternak, String luas, String idJenisHewan,
                          String kapasitas,
                          String jenisKandang, String nilaiBangunan, String alamat, String namaKandang,
                          String latitude, String longitude, String nikPeternak, JenisHewan jenisHewan, Peternak peternak,
                          String namaPeternak) {
        this.idKandang = idKandang;
        this.idPeternak = idPeternak;
        this.luas = luas;
        this.idJenisHewan = idJenisHewan;
        this.kapasitas = kapasitas;
        this.nikPeternak = nikPeternak;
        this.jenisKandang = jenisKandang;
        this.nilaiBangunan = nilaiBangunan;
        this.alamat = alamat;
        this.namaKandang = namaKandang;
        this.latitude = latitude;
        this.longitude = longitude;
        this.jenisHewan = jenisHewan;
        this.peternak = peternak;
        this.namaPeternak = namaPeternak;
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

    public String getidPeternak() {
        return idPeternak;
    }


    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    public String getIdJenisHewan() {
        return idJenisHewan;
    }

    public void setIdJenisHewan(String idJenisHewan) {
        this.idJenisHewan = idJenisHewan;
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

    public JenisHewan getJenisHewan() {
        return jenisHewan;
    }

    public void setJenisHewan(JenisHewan jenisHewan) {
        this.jenisHewan = jenisHewan;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getJenis() {
        return jenis;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKandang":
                this.idKandang = value;
                break;
            case "idPeternak":
                this.idPeternak = value;
                break;
            case "luas":
                this.luas = value;
                break;
            case "idJenisHewan":
                this.idJenisHewan = value;
                break;
            case "jenis":
                this.jenis = value;
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