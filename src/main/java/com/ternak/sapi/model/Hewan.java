package com.ternak.sapi.model;

public class Hewan {
    private String idHewan;
    private String idIsikhnasTernak;
    private String kodeEartagNasional;
    private String nama;
    private String noKartuTernak;
    private Petugas petugas;
    private Peternak peternak;
    private Kandang kandang;
    private JenisHewan jenisHewan;
    private RumpunHewan rumpunHewan;
    private TujuanPemeliharaan tujuanPemeliharaan;
    private String sex;
    private String umur;
    private String tanggalLahir;
    private String tempatLahir;
    private String identifikasiHewan;
    private String tanggalTerdaftar;
    private String latitude;
    private String longitude;
    private String file_path;
    private String idIsikhnas;

    public Hewan() {
    }

    public Hewan(String idHewan, String kodeEartagNasional, String noKartuTernak, Petugas petugas, Peternak peternak,
            Kandang kandang,
            String sex, String umur, String identifikasiHewan, String tanggalTerdaftar, String latitude,
            String longitude, String file_path, String idIsikhnasTernak, String nama,
            JenisHewan jenisHewan, RumpunHewan rumpunHewan, String tanggalLahir, String tempatLahir,
            String idIsikhnas,TujuanPemeliharaan tujuanPemeliharaan) {
        this.idHewan = idHewan;
        this.idIsikhnasTernak = idIsikhnasTernak;
        this.kodeEartagNasional = kodeEartagNasional;
        this.noKartuTernak = noKartuTernak;
        this.nama = nama;
        this.petugas = petugas;
        this.peternak = peternak;
        this.kandang = kandang;
        this.jenisHewan = jenisHewan;
        this.rumpunHewan = rumpunHewan;
        this.sex = sex;
        this.umur = umur;
        this.tanggalLahir = tanggalLahir;
        this.tempatLahir = tempatLahir;
        this.tujuanPemeliharaan = tujuanPemeliharaan;
        this.identifikasiHewan = identifikasiHewan;
        this.tanggalTerdaftar = tanggalTerdaftar;
        this.latitude = latitude;
        this.longitude = longitude;
        this.file_path = file_path;
        this.idIsikhnas = idIsikhnas;
    }

    public String getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(String idHewan) {
        this.idHewan = idHewan;
    }

    public String getIdIsikhnasTernak() {
        return idIsikhnasTernak;
    }

    public void setIdIsikhnasTernak(String idIsikhnasTernak) {
        this.idIsikhnasTernak = idIsikhnasTernak;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKodeEartagNasional() {
        return kodeEartagNasional;
    }

    public void setKodeEartagNasional(String kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public String getNoKartuTernak() {
        return noKartuTernak;
    }

    public void setNoKartuTernak(String noKartuTernak) {
        this.noKartuTernak = noKartuTernak;
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

    public Peternak getPeternak() {
        return peternak;
    }

    public void setPeternak(Peternak peternak) {
        this.peternak = peternak;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public void setPetugas(Petugas petugas) {
        this.petugas = petugas;
    }

    public Kandang getKandang() {
        return kandang;
    }

    public void setKandang(Kandang kandang) {
        this.kandang = kandang;
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

    public JenisHewan getJenisHewan() {
        return jenisHewan;
    }

    public void setJenisHewan(JenisHewan jenisHewan) {
        this.jenisHewan = jenisHewan;
    }

    public RumpunHewan getRumpunHewan() {
        return rumpunHewan;
    }

    public void setRumpunHewan(RumpunHewan rumpunHewan) {
        this.rumpunHewan = rumpunHewan;
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

    public TujuanPemeliharaan getTujuanPemeliharaan() {
        return tujuanPemeliharaan;
    }

    public void setTujuanPemeliharaan(TujuanPemeliharaan tujuanPemeliharaan) {
        this.tujuanPemeliharaan = tujuanPemeliharaan;
    }

    public String getIdIsikhnas() {
        return idIsikhnas;
    }

    public void setIdIsikhnas(String idIsikhnas) {
        this.idIsikhnas = idIsikhnas;
    }

    public boolean isValid() {
        return this.idHewan != null &&
                this.petugas != null &&
                this.peternak != null &&
                this.tanggalLahir != null &&
                this.tempatLahir != null;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idHewan":
                this.idHewan = value;
                break;
            case "idIsikhnasTernak":
                this.idIsikhnasTernak = value;
                break;
            case "kodeEartagNasional":
                this.kodeEartagNasional = value;
                break;
            case "noKartuTernak":
                this.noKartuTernak = value;
                break;
            case "nama":
                this.nama = value;
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
            case "tempatLahir":
                this.tempatLahir = value;
                break;
            case "identifikasiHewan":
                this.identifikasiHewan = value;
                break;
            case "tanggalTerdaftar":
                this.tanggalTerdaftar = value;
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
            case "idIsikhnas":
                this.idIsikhnas = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}