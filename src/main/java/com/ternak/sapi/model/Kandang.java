package com.ternak.sapi.model;

public class Kandang {
    private String idKandang;
    private Peternak peternak;
    private String luas;
    private String jenisKandang;
    private JenisHewan jenisHewan;
    private String kapasitas;
    private String nilaiBangunan;
    private String alamat;
    private String latitude;
    private String longitude;
    private String file_path;
    private String namaKandang;
    private String nikPeternak;
    private String idJenisHewan;
    public Kandang() {
    }

    public Kandang(String idKandang, Peternak peternak, String luas, JenisHewan jenisHewan,
            String kapasitas,
            String nilaiBangunan, String jenisKandang, String alamat, String latitude, String longitude,
            String file_path, String namaKandang, String nikPeternak, String idJenisHewan) {
        this.idKandang = idKandang;
        this.peternak = peternak;
        this.luas = luas;
        this.jenisHewan = jenisHewan;
        this.kapasitas = kapasitas;
        this.nilaiBangunan = nilaiBangunan;
        this.jenisKandang = jenisKandang;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.file_path = file_path;
        this.namaKandang = namaKandang;
        this.nikPeternak = nikPeternak;
        this.idJenisHewan = idJenisHewan;
    }

    public String getIdKandang() {
        return idKandang;
    }

    public void setIdKandang(String idKandang) {
        this.idKandang = idKandang;
    }

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

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    public JenisHewan getJenisHewan() {
        return jenisHewan;
    }

    public void setJenisHewan(JenisHewan jenisHewan) {
        this.jenisHewan = jenisHewan;
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

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getNamaKandang() {
        return namaKandang;
    }

    public void setNamaKandang(String namaKandang) {
        this.namaKandang = namaKandang;
    }

    public String getIdJenisHewan() {
        return idJenisHewan;
    }

    public void setIdJenisHewan(String idJenisHewan) {
        this.idJenisHewan = idJenisHewan;
    }


    public boolean isValid() {
        return this.idKandang != null &&
                this.peternak != null &&
                this.luas != null &&
                this.jenisHewan != null &&
                this.kapasitas != null &&
                this.jenisKandang != null &&
                this.nilaiBangunan != null &&
                this.alamat != null &&
                this.latitude != null &&
                this.longitude != null &&
                this.file_path != null &&
                this.namaKandang != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKandang":
                this.idKandang = value;
                break;
            case "idJenisHewan":
                this.idJenisHewan = value;
                break;
            case "luas":
                this.luas = value;
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
            case "file_path":
                this.file_path = value;
                break;
            case "namaKandang":
                this.namaKandang = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}