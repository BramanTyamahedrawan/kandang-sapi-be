package com.ternak.sapi.payload;

public class HewanRequest {
    private String idHewan;
    private String kodeEartagNasional;
    private String petugas_id;
    private String kandang_id;
    private String noKartuTernak;
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

    // jenis hewan
    private String jenis;
    private String jenisHewanId;

    // peternak
    private String nikPeternak;
    private String namaPeternak;
    private String dusun;
    private String desa;
    private String kecamatan;
    private String kabupaten;
    private String alamat;
    private String provinsi;
    private String email;
    private String jenisKelamin;
    private String noTelepon;
    private String lokasi;
    private String idIsikhnas;
    private String tanggalPendaftaran;
    private String peternak_id;

    // petugas
    private String nikPetugas;
    private String namaPetugas;
    private String emailPetugas;
    private String noTeleponPetugas;
    private String job;
    private String wilayah;

    public HewanRequest() {
    }

    public HewanRequest(String idHewan, String kodeEartagNasional, String noKartuTernak, String petugas_id,
            String peternak_id,
            String kandang_id, String rumpunHewanId, String jenisHewanId, String sex, String umur, String tanggalLahir,
            String identifikasiHewan, String tanggalTerdaftar, String latitude, String longitude, String tempatLahir,
            String idIsikhnas, String nikPetugas, String nikPeternak, String jenis, String namaPeternak, String dusun,
            String desa, String kecamatan, String kabupaten, String alamat, String provinsi, String email,
            String jenisKelamin, String noTelepon, String lokasi, String tanggalPendaftaran, String tujuanPemeliharaan,
            String jenisHewan, String namaPetugas, String emailPetugas, String noTeleponPetugas, String job,
            String wilayah) {
        this.idHewan = idHewan;
        this.kodeEartagNasional = kodeEartagNasional;
        this.noKartuTernak = noKartuTernak;
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
        this.jenis = jenis;
        this.namaPeternak = namaPeternak;
        this.dusun = dusun;
        this.desa = desa;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.alamat = alamat;
        this.provinsi = provinsi;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.noTelepon = noTelepon;
        this.lokasi = lokasi;
        this.tanggalPendaftaran = tanggalPendaftaran;
        this.tujuanPemeliharaan = tujuanPemeliharaan;
        this.namaPetugas = namaPetugas;
        this.emailPetugas = emailPetugas;
        this.noTeleponPetugas = noTeleponPetugas;
        this.job = job;
        this.wilayah = wilayah;
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

    public String getNoKartuTernak() {
        return noKartuTernak;
    }

    public void setNoKartuTernak(String noKartuTernak) {
        this.noKartuTernak = noKartuTernak;
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

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    // peternak
    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
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

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTanggalPendaftaran(String tanggalPendaftaran) {
        this.tanggalPendaftaran = tanggalPendaftaran;
    }

    // petugas
    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getEmailPetugas() {
        return emailPetugas;
    }

    public void setEmailPetugas(String emailPetugas) {
        this.emailPetugas = emailPetugas;
    }

    public String getNoTeleponPetugas() {
        return noTeleponPetugas;
    }

    public void setNoTeleponPetugas(String noTeleponPetugas) {
        this.noTeleponPetugas = noTeleponPetugas;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idHewan":
                this.idHewan = value;
                break;
            case "kodeEartagNasional":
                this.kodeEartagNasional = value;
                break;
            case "noKartuTernak":
                this.noKartuTernak = value;
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
            case "jenis":
                this.jenis = value;
                break;
            case "namaPeternak":
                this.namaPeternak = value;
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
            case "provinsi":
                this.provinsi = value;
                break;
            case "email":
                this.email = value;
                break;
            case "jenisKelamin":
                this.jenisKelamin = value;
                break;
            case "noTelepon":
                this.noTelepon = value;
                break;
            case "lokasi":
                this.lokasi = value;
                break;
            case "tanggalPendaftaran":
                this.tanggalPendaftaran = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
