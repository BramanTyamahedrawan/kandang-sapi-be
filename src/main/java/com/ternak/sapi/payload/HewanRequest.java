package com.ternak.sapi.payload;

public class HewanRequest {
    private String idHewan;
    private String kodeEartagNasional;
    private String noKartuTernak;
    private String sex;
    private String umur;
    private String tanggalLahir;
    private String tempatLahir;
    private String identifikasiHewan;
    private String tanggalTerdaftar;
    private String latitude;
    private String longitude;

    // jenis hewan
    private String jenisHewanId;
    private String jenis;
    private String deskripsiJenisHewan;

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
    private String idPeternak;

    // petugas
    private String petugasId;
    private String nikPetugas;
    private String namaPetugas;
    private String emailPetugas;
    private String noTeleponPetugas;
    private String job;
    private String wilayah;

    // Kandang
    private String idKandang;
    private String kapasitas;
    private String jenisKandang;
    private String nilaiBangunan;
    private String alamatKandang;
    private String namaKandang;
    private String latitudeKandang;
    private String longitudeKandang;
    private String luas;

    // Rumpun Hewan
    private String rumpun;
    private String deskripsiRumpun;
    private String rumpunHewanId;

    // Tujuan Pemeliharaan
    private String idTujuanPemeliharaan;
    private String tujuanPemeliharaan;
    private String deskripsiTujuanPemeliharaan;

    public HewanRequest() {
    }

    public HewanRequest(String idHewan, String kodeEartagNasional, String noKartuTernak, String petugasId,
            String idPeternak,
            String idKandang, String rumpunHewanId, String jenisHewanId, String sex, String umur, String tanggalLahir,
            String identifikasiHewan, String tanggalTerdaftar, String latitude, String longitude, String tempatLahir,
            String idIsikhnas, String nikPetugas, String nikPeternak, String jenis, String namaPeternak, String dusun,
            String desa, String kecamatan, String kabupaten, String alamat, String provinsi, String email,
            String jenisKelamin, String noTelepon, String lokasi, String tanggalPendaftaran, String tujuanPemeliharaan,
            String jenisHewan, String namaPetugas, String emailPetugas, String noTeleponPetugas, String job,
            String wilayah, String rumpun, String namaKandang, String deskripsiRumpun, String deskripsiJenisHewan,
            String deskripsiTujuanPemeliharaan, String idTujuanPemeliharaan, String kapasitas, String jenisKandang,
            String nilaiBangunan, String alamatKandang, String latitudeKandang, String longitudeKandang, String luas) {
        this.idHewan = idHewan;
        this.kodeEartagNasional = kodeEartagNasional;
        this.noKartuTernak = noKartuTernak;
        this.petugasId = petugasId;
        this.idPeternak = idPeternak;
        this.idKandang = idKandang;
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
        this.rumpun = rumpun;
        this.namaKandang = namaKandang;
        this.deskripsiRumpun = deskripsiRumpun;
        this.deskripsiJenisHewan = deskripsiJenisHewan;
        this.deskripsiTujuanPemeliharaan = deskripsiTujuanPemeliharaan;
        this.idTujuanPemeliharaan = idTujuanPemeliharaan;
        this.kapasitas = kapasitas;
        this.jenisKandang = jenisKandang;
        this.nilaiBangunan = nilaiBangunan;
        this.alamatKandang = alamatKandang;
        this.latitudeKandang = latitudeKandang;
        this.longitudeKandang = longitudeKandang;
        this.luas = luas;
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

    public void setPetugasId(String petugasId) {
        this.petugasId = petugasId;
    }

    public String getPetugasId() {
        return petugasId;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getIdKandang() {
        return idKandang;
    }

    public void setIdKandang(String idKandang) {
        this.idKandang = idKandang;
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

    public void setDeskripsiJenisHewan(String deskripsiJenisHewan) {
        this.deskripsiJenisHewan = deskripsiJenisHewan;
    }

    public String getDeskripsiJenisHewan() {
        return deskripsiJenisHewan;
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

    // Kandang
    public String getNamaKandang() {
        return namaKandang;
    }

    public void setNamaKandang(String namaKandang) {
        this.namaKandang = namaKandang;
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

    public String getAlamatKandang() {
        return alamatKandang;
    }

    public void setAlamatKandang(String alamatKandang) {
        this.alamatKandang = alamatKandang;
    }

    public String getLatitudeKandang() {
        return latitudeKandang;
    }

    public void setLatitudeKandang(String latitudeKandang) {
        this.latitudeKandang = latitudeKandang;
    }

    public String getLongitudeKandang() {
        return longitudeKandang;
    }

    public void setLongitudeKandang(String longitudeKandang) {
        this.longitudeKandang = longitudeKandang;
    }

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    // Rumpun
    public String getRumpun() {
        return rumpun;
    }

    public void setRumpun(String rumpun) {
        this.rumpun = rumpun;
    }

    public void setDeskripsiRumpun(String deskripsiRumpun) {
        this.deskripsiRumpun = deskripsiRumpun;
    }

    public String getDeskripsiRumpun() {
        return deskripsiRumpun;
    }

    public void setDeskripsiTujuanPemeliharaan(String deskripsiTujuanPemeliharaan) {
        this.deskripsiTujuanPemeliharaan = deskripsiTujuanPemeliharaan;
    }

    public String getDeskripsiTujuanPemeliharaan() {
        return deskripsiTujuanPemeliharaan;
    }

    public void setIdTujuanPemeliharaan(String idTujuanPemeliharaan) {
        this.idTujuanPemeliharaan = idTujuanPemeliharaan;
    }

    public String getIdTujuanPemeliharaan() {
        return idTujuanPemeliharaan;
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
            case "petugasId":
                this.petugasId = value;
                break;
            case "idPeternak":
                this.idPeternak = value;
                break;
            case "idKandang":
                this.idKandang = value;
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
            case "rumpun":
                this.rumpun = value;
                break;
            case "tujuanPemeliharaan":
                this.tujuanPemeliharaan = value;
                break;
            case "namaKandang":
                this.namaKandang = value;
                break;
            case "deskripsiRumpun":
                this.deskripsiRumpun = value;
                break;
            case "idTujuanPemeliharaan":
                this.idTujuanPemeliharaan = value;
                break;
            case "deskripsiTujuanPemeliharaan":
                this.deskripsiTujuanPemeliharaan = value;
                break;
            case "deskripsiJenisHewan":
                this.deskripsiJenisHewan = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
