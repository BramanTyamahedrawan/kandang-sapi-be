package com.ternak.sapi.payload;

public class HewanRequest {
    private String idHewan;
    private String kodeEartagNasional;
    private String petugas_id;
    private String peternak_id;
    private String kandang_id;
    private String jenisHewanId;
    private String rumpunHewanId;
    private String sex;
    private String umur;
    private String tanggalLahir;
    private String tempatLahir;
    private String tujuanPemeliharaan;
    private String identifikasiHewan;
    private String tanggalTerdaftar;
    private String latitude;
    private String longitude;
    private String idIsikhnas;
    private String nikPetugas;
    private String nikPeternak;

    public HewanRequest() {
    }

    public HewanRequest(String idHewan, String kodeEartagNasional, String petugas_id, String peternak_id,
            String kandang_id, String rumpunHewanId, String jenisHewanId, String sex, String umur, String tanggalLahir,
            String identifikasiHewan, String tanggalTerdaftar, String latitude, String longitude, String tempatLahir,
            String idIsikhnas, String nikPetugas, String nikPeternak) {
        this.idHewan = idHewan;
        this.kodeEartagNasional = kodeEartagNasional;
        this.petugas_id = petugas_id;
        this.peternak_id = peternak_id;
        this.kandang_id = kandang_id;
        this.jenisHewanId = jenisHewanId;
        this.rumpunHewanId = rumpunHewanId;
        this.sex = sex;
        this.umur = umur;
        this.tanggalLahir = tanggalLahir;
        this.identifikasiHewan = identifikasiHewan;
        this.tanggalTerdaftar = tanggalTerdaftar;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tempatLahir = tempatLahir;
        this.idIsikhnas = idIsikhnas;
        this.nikPetugas = nikPetugas;
        this.nikPeternak = nikPeternak;
    }

    public String getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(String idHewan) {
        this.idHewan = idHewan;
    }

    public String getKodeEartagNasional() {
        return kodeEartagNasional;
    }

    public void setKodeEartagNasional(String kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public String getPetugas_id() {
        return petugas_id;
    }

    public void setPetugas_id(String petugas_id) {
        this.petugas_id = petugas_id;
    }

    public String getPeternak_id() {
        return peternak_id;
    }

    public void setPeternak_id(String peternak_id) {
        this.peternak_id = peternak_id;
    }

    public String getKandang_id() {
        return kandang_id;
    }

    public void setKandang_id(String kandang_id) {
        this.kandang_id = kandang_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getIdentifikasiHewan() {
        return identifikasiHewan;
    }

    public void setIdentifikasiHewan(String identifikasiHewan) {
        this.identifikasiHewan = identifikasiHewan;
    }

    public String getTanggalTerdaftar() {
        return tanggalTerdaftar;
    }

    public void setTanggalTerdaftar(String tanggalTerdaftar) {
        this.tanggalTerdaftar = tanggalTerdaftar;
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

    public String getJenisHewanId() {
        return jenisHewanId;
    }

    public void setJenisHewanId(String jenisHewanId) {
        this.jenisHewanId = jenisHewanId;
    }

    public String getRumpunHewanId() {
        return rumpunHewanId;
    }

    public void setRumpunHewanId(String rumpunHewanId) {
        this.rumpunHewanId = rumpunHewanId;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTujuanPemeliharaan() {
        return tujuanPemeliharaan;
    }

    public void setTujuanPemeliharaan(String tujuanPemeliharaan) {
        this.tujuanPemeliharaan = tujuanPemeliharaan;
    }

    public String getIdIsikhnas() {
        return idIsikhnas;
    }

    public void setIdIsikhnas(String idIsikhnas) {
        this.idIsikhnas = idIsikhnas;
    }

    public String getNikPetugas() {
        return nikPetugas;
    }

    public void setNikPetugas(String nikPetugas) {
        this.nikPetugas = nikPetugas;
    }

    public String getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(String nikPeternak) {
        this.nikPeternak = nikPeternak;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idHewan":
                this.idHewan = value;
                break;
            case "kodeEartagNasional":
                this.kodeEartagNasional = value;
                break;
            case "petugas_id":
                this.petugas_id = value;
                break;
            case "peternak_id":
                this.peternak_id = value;
                break;
            case "kandang_id":
                this.kandang_id = value;
                break;
            case "sex":
                this.sex = value;
                break;
            case "umur":
                this.umur = value;
                break;
            case "tanggalLahir":
                this.tanggalLahir = value;
                break;
            case "identifikasiHewan":
                this.identifikasiHewan = value;
                break;
            case "tanggalTerdaftar":
                this.tanggalTerdaftar = value;
                break;
            case "tempatLahir":
                this.tempatLahir = value;
                break;
            case "latitude":
                this.latitude = value;
                break;
            case "longitude":
                this.longitude = value;
                break;
            case "idIsikhnas":
                this.idIsikhnas = value;
                break;
            case "nikPetugas":
                this.nikPetugas = value;
                break;
            case "nikPeternak":
                this.nikPeternak = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
