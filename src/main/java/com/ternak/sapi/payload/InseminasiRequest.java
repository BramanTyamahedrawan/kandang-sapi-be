package com.ternak.sapi.payload;

public class InseminasiRequest {
    private String idInseminasi;
    private String tanggalIB;
    private String ib1;
    private String ib2;
    private String ib3;
    private String ibLain;
    private String idPejantan;
    private String idPembuatan;
    private String bangsaPejantan;
    private String produsen;
    private String lokasi;
    private String desa;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;

    // peternak
    private String idPeternak;
    private String nikPeternak;
    private String namaPeternak;
    private String alamat;
    private String email;
    private String jenisKelamin;
    private String noTelepon;
    private String idIsikhnas;
    private String tanggalPendaftaran;
    private String lokasiPeternak;
    private String provinsiPeternak;
    private String kabupatenPeternak;
    private String kecamatanPeternak;
    private String desaPeternak;
    private String dusunPeternak;
    private String tanggalLahirPeternak;

    // hewan
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

    // Petugas
    private String petugasId;
    private String nikPetugas;
    private String namaPetugas;
    private String emailPetugas;
    private String noTelp;
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

    // Jenis Hewan
    private String idJenisHewan;
    private String jenis;
    private String deskripsiJenis;

    // Rumpun Hewan
    private String idRumpunHewan;
    private String rumpun;
    private String deskripsiRumpun;

    public InseminasiRequest() {
    }

    public InseminasiRequest(String idInseminasi, String tanggalIB, String idPeternak,
            String idHewan, String petugasId, String ib, String idPejantan, String idPembuatan,
            String bangsaPejantan, String produsen, String lokasi, String desa, String kecamatan, String kabupaten,
            String provinsi, String ib1, String ib2, String ib3, String ibLain, String namaPeternak, String alamat,
            String email, String jenisKelamin, String noTelepon, String idIsikhnas, String tanggalPendaftaran,
            String kodeEartagNasional, String noKartuTernak, String sex, String umur, String tanggalLahir,
            String tempatLahir, String identifikasiHewan, String tanggalTerdaftar, String latitude, String longitude,
            String nikPetugas, String namaPetugas, String emailPetugas, String noTelp, String job, String wilayah,
            String lokasiPeternak, String provinsiPeternak, String kabupatenPeternak, String kecamatanPeternak,
            String desaPeternak, String dusunPeternak, String nikPeternak, String tanggalLahirPeternak,
            String idKandang, String kapasitas, String jenisKandang, String nilaiBangunan, String alamatKandang,
            String namaKandang, String latitudeKandang, String longitudeKandang, String luas, String idJenisHewan,
            String jenis, String deskripsiJenis, String idRumpunHewan, String rumpun, String deskripsiRumpun) {
        this.idInseminasi = idInseminasi;
        this.tanggalIB = tanggalIB;
        this.idPeternak = idPeternak;
        this.idHewan = idHewan;
        this.petugasId = petugasId;
        this.ib1 = ib1;
        this.ib2 = ib2;
        this.ib3 = ib3;
        this.ibLain = ibLain;
        this.idPejantan = idPejantan;
        this.idPembuatan = idPembuatan;
        this.bangsaPejantan = bangsaPejantan;
        this.produsen = produsen;
        this.lokasi = lokasi;
        this.desa = desa;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.provinsi = provinsi;
        this.namaPeternak = namaPeternak;
        this.alamat = alamat;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.noTelepon = noTelepon;
        this.idIsikhnas = idIsikhnas;
        this.tanggalPendaftaran = tanggalPendaftaran;
        this.kodeEartagNasional = kodeEartagNasional;
        this.noKartuTernak = noKartuTernak;
        this.sex = sex;
        this.umur = umur;
        this.tanggalLahir = tanggalLahir;
        this.tempatLahir = tempatLahir;
        this.identifikasiHewan = identifikasiHewan;
        this.tanggalTerdaftar = tanggalTerdaftar;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nikPetugas = nikPetugas;
        this.namaPetugas = namaPetugas;
        this.emailPetugas = emailPetugas;
        this.noTelp = noTelp;
        this.job = job;
        this.wilayah = wilayah;
        this.lokasiPeternak = lokasiPeternak;
        this.provinsiPeternak = provinsiPeternak;
        this.kabupatenPeternak = kabupatenPeternak;
        this.kecamatanPeternak = kecamatanPeternak;
        this.desaPeternak = desaPeternak;
        this.dusunPeternak = dusunPeternak;
        this.nikPeternak = nikPeternak;
        this.tanggalLahirPeternak = tanggalLahirPeternak;
        // kandang
        this.idKandang = idKandang;
        this.kapasitas = kapasitas;
        this.jenisKandang = jenisKandang;
        this.nilaiBangunan = nilaiBangunan;
        this.alamatKandang = alamatKandang;
        this.namaKandang = namaKandang;
        this.latitudeKandang = latitudeKandang;
        this.longitudeKandang = longitudeKandang;
        this.luas = luas;
        // jenis hewan
        this.idJenisHewan = idJenisHewan;
        this.jenis = jenis;
        this.deskripsiJenis = deskripsiJenis;
        // rumpun hewan
        this.idRumpunHewan = idRumpunHewan;
        this.rumpun = rumpun;
        this.deskripsiRumpun = deskripsiRumpun;
    }

    public String getIdInseminasi() {
        return idInseminasi;
    }

    public void setIdInseminasi(String idInseminasi) {
        this.idInseminasi = idInseminasi;
    }

    public String getTanggalIB() {
        return tanggalIB;
    }

    public void setTanggalIB(String tanggalIB) {
        this.tanggalIB = tanggalIB;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(String idHewan) {
        this.idHewan = idHewan;
    }

    public String getPetugasId() {
        return petugasId;
    }

    public void setPetugasId(String petugasId) {
        this.petugasId = petugasId;
    }

    public String getIb1() {
        return ib1;
    }

    public void setIb1(String ib1) {
        this.ib1 = ib1;
    }

    public String getIb2() {
        return ib2;
    }

    public void setIb2(String ib2) {
        this.ib2 = ib2;
    }

    public String getIb3() {
        return ib3;
    }

    public void setIb3(String ib3) {
        this.ib3 = ib3;
    }

    public String getIbLain() {
        return ibLain;
    }

    public void setIbLain(String ibLain) {
        this.ibLain = ibLain;
    }

    public String getIdPejantan() {
        return idPejantan;
    }

    public void setIdPejantan(String idPejantan) {
        this.idPejantan = idPejantan;
    }

    public String getIdPembuatan() {
        return idPembuatan;
    }

    public void setIdPembuatan(String idPembuatan) {
        this.idPembuatan = idPembuatan;
    }

    public String getBangsaPejantan() {
        return bangsaPejantan;
    }

    public void setBangsaPejantan(String bangsaPejantan) {
        this.bangsaPejantan = bangsaPejantan;
    }

    public String getProdusen() {
        return produsen;
    }

    public void setProdusen(String produsen) {
        this.produsen = produsen;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
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

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    // Peternak

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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
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

    public String getIdIsikhnas() {
        return idIsikhnas;
    }

    public void setIdIsikhnas(String idIsikhnas) {
        this.idIsikhnas = idIsikhnas;
    }

    public String getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTanggalPendaftaran(String tanggalPendaftaran) {
        this.tanggalPendaftaran = tanggalPendaftaran;
    }

    public String getLokasiPeternak() {
        return lokasiPeternak;
    }

    public void setLokasiPeternak(String lokasiPeternak) {
        this.lokasiPeternak = lokasiPeternak;
    }

    public String getProvinsiPeternak() {
        return provinsiPeternak;
    }

    public void setProvinsiPeternak(String provinsiPeternak) {
        this.provinsiPeternak = provinsiPeternak;
    }

    public String getKabupatenPeternak() {
        return kabupatenPeternak;
    }

    public void setKabupatenPeternak(String kabupatenPeternak) {
        this.kabupatenPeternak = kabupatenPeternak;
    }

    public String getKecamatanPeternak() {
        return kecamatanPeternak;
    }

    public void setKecamatanPeternak(String kecamatanPeternak) {
        this.kecamatanPeternak = kecamatanPeternak;
    }

    public String getDesaPeternak() {
        return desaPeternak;
    }

    public void setDesaPeternak(String desaPeternak) {
        this.desaPeternak = desaPeternak;
    }

    public String getDusunPeternak() {
        return dusunPeternak;
    }

    public void setDusunPeternak(String dusunPeternak) {
        this.dusunPeternak = dusunPeternak;
    }

    public String getTanggalLahirPeternak() {
        return tanggalLahirPeternak;
    }

    public void setTanggalLahirPeternak(String tanggalLahirPeternak) {
        this.tanggalLahirPeternak = tanggalLahirPeternak;
    }

    // Hewan

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

    // Petugas
    public String getNikPetugas() {
        return nikPetugas;
    }

    public void setNikPetugas(String nikPetugas) {
        this.nikPetugas = nikPetugas;
    }

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

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
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
    public String getIdKandang() {
        return idKandang;
    }

    public void setIdKandang(String idKandang) {
        this.idKandang = idKandang;
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

    public String getNamaKandang() {
        return namaKandang;
    }

    public void setNamaKandang(String namaKandang) {
        this.namaKandang = namaKandang;
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

    // Jenis Hewan

    public String getIdJenisHewan() {
        return idJenisHewan;
    }

    public void setIdJenisHewan(String idJenisHewan) {
        this.idJenisHewan = idJenisHewan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getDeskripsiJenis() {
        return deskripsiJenis;
    }

    public void setDeskripsiJenis(String deskripsiJenis) {
        this.deskripsiJenis = deskripsiJenis;
    }

    // Rumpun Hewan

    public String getIdRumpunHewan() {
        return idRumpunHewan;
    }

    public void setIdRumpunHewan(String idRumpunHewan) {
        this.idRumpunHewan = idRumpunHewan;
    }

    public String getRumpun() {
        return rumpun;
    }

    public void setRumpun(String rumpun) {
        this.rumpun = rumpun;
    }

    public String getDeskripsiRumpun() {
        return deskripsiRumpun;
    }

    public void setDeskripsiRumpun(String deskripsiRumpun) {
        this.deskripsiRumpun = deskripsiRumpun;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idInseminasi":
                this.idInseminasi = value;
                break;
            case "tanggalIB":
                this.tanggalIB = value;
                break;
            case "idPeternak":
                this.idPeternak = value;
                break;
            case "idHewan":
                this.idHewan = value;
                break;
            case "petugasId":
                this.petugasId = value;
                break;
            case "ib1":
                this.ib1 = value;
                break;
            case "ib2":
                this.ib2 = value;
                break;
            case "ib3":
                this.ib3 = value;
                break;
            case "ibLain":
                this.ibLain = value;
                break;
            case "idPejantan":
                this.idPejantan = value;
                break;
            case "idPembuatan":
                this.idPembuatan = value;
                break;
            case "bangsaPejantan":
                this.bangsaPejantan = value;
                break;
            case "produsen":
                this.produsen = value;
                break;
            case "lokasi":
                this.lokasi = value;
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
            case "provinsi":
                this.provinsi = value;
                break;
            case "namaPeternak":
                this.namaPeternak = value;
                break;
            case "alamat":
                this.alamat = value;
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
            case "idIsikhnas":
                this.idIsikhnas = value;
                break;
            case "tanggalPendaftaran":
                this.tanggalPendaftaran = value;
                break;
            case "kodeEartagNasional":
                this.kodeEartagNasional = value;
                break;
            case "noKartuTernak":
                this.noKartuTernak = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
