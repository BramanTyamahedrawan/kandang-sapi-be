package com.ternak.sapi.model;

public class Peternak {
    private String idPeternak;
    private String petugasId;
    private String nikPeternak;
    private String namaPeternak;
    private String lokasi;
    private Petugas petugas;
    private String tanggalPendaftaran;
    private String noTelepon;
    private String email;
    private String jenisKelamin;
    private String tanggalLahir;
    private String idIsikhnas;
    private String dusun;
    private String desa;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;
    private String alamat;
    private String latitude;
    private String longitude;

    public Peternak() {
    }

    public Peternak(String idPeternak, String nikPeternak, String namaPeternak, String lokasi, Petugas petugas,
            String tanggalPendaftaran, String noTelepon, String email, String jenisKelamin, String tanggalLahir,
            String idIsikhnas, String dusun, String desa, String kecamatan, String kabupaten, String alamat,
            String latitude, String longitude, String provinsi) {
        this.idPeternak = idPeternak;
        this.nikPeternak = nikPeternak;
        this.namaPeternak = namaPeternak;
        this.lokasi = lokasi;
        this.petugas = petugas;
        this.tanggalPendaftaran = tanggalPendaftaran;
        this.noTelepon = noTelepon;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.tanggalLahir = tanggalLahir;
        this.idIsikhnas = idIsikhnas;
        this.dusun = dusun;
        this.desa = desa;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.alamat = alamat;
        this.latitude = latitude;
        this.longitude = longitude;
        this.provinsi = provinsi;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(String nikPeternak) {
        this.nikPeternak = nikPeternak;
    }

    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public void setPetugas(Petugas petugas) {
        this.petugas = petugas;
    }

    public String getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTanggalPendaftaran(String tanggalPendaftaran) {
        this.tanggalPendaftaran = tanggalPendaftaran;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdIsikhnas() {
        return idIsikhnas;
    }

    public void setIdIsikhnas(String idIsikhnas) {
        this.idIsikhnas = idIsikhnas;
    }

    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
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

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public void setPetugasId(String petugasId) {
        this.petugasId = petugasId;
    }

    public String getPetugasId() {
        return petugasId;
    }

    public boolean isValid() {
        return this.idPeternak != null &&
                this.nikPeternak != null &&
                this.namaPeternak != null &&
                this.lokasi != null &&
                this.jenisKelamin != null &&
                this.tanggalLahir != null &&
                this.noTelepon != null &&
                this.email != null &&
                this.idIsikhnas != null &&
                this.tanggalPendaftaran != null &&
                this.dusun != null &&
                this.desa != null &&
                this.kecamatan != null &&
                this.kabupaten != null &&
                this.alamat != null &&
                this.latitude != null &&
                this.longitude != null &&
                this.provinsi != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idPeternak":
                this.idPeternak = value;
                break;
            case "petugasId":
                this.petugasId = value;
                break;
            case "nikPeternak":
                this.nikPeternak = value;
                break;
            case "namaPeternak":
                this.namaPeternak = value;
                break;
            case "lokasi":
                this.lokasi = value;
                break;
            case "jenisKelamin":
                this.jenisKelamin = value;
                break;
            case "tanggalLahir":
                this.tanggalLahir = value;
                break;
            case "noTelepon":
                this.noTelepon = value;
                break;
            case "email":
                this.email = value;
                break;
            case "idIsikhnas":
                this.idIsikhnas = value;
                break;
            case "dusun":
                this.dusun = value;
                break;
            case "desa":
                this.desa = value;
                break;
            case "kecamatan":
                this.kecamatan = value;
                break;
            case "kabupaten":
                this.kabupaten = value;
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
            case "provinsi":
                this.provinsi = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
